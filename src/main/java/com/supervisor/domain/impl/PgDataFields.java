package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.datasource.comparators.Matchers;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataFields;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.EditableDataFields;
import com.supervisor.domain.FormularDataFields;
import com.supervisor.domain.ListDataFields;
import com.supervisor.domain.ParamDataFields;
import com.supervisor.domain.SimpleDataFields;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataFields;

public final class PgDataFields extends DomainRecordables<DataField, DataFields> implements DataFields {

	private final DataModel model;
	
	public PgDataFields(final DataModel model) throws IOException {
		this(viewSource(model), model);
	}
	
	private PgDataFields(final RecordSet<DataField> source, final DataModel model) throws IOException {
		super(PxDataField.class, source);
		
		this.model = model;
		this.source = source.orderBy(DataField::id);
	}
	
	private static String scriptOf(final DataModel model) {
		final Table table = new TableImpl(DataField.class);
		return String.format(
					"select src.* \r\n" + 
					"from %s as src \r\n" + 
					"where src.model_id = %s",
					table.name(),									
					model.id()
			   );
	}
	
	private static RecordSet<DataField> viewSource(final DataModel model) throws IOException{

		String viewScript = StringUtils.EMPTY;
		DataModel currentModel = model;
		do {
			if(StringUtils.isBlank(viewScript)) {
				viewScript = scriptOf(currentModel);
			} else {
				viewScript = String.format(
								"%s \r\n" + 
								"UNION ALL \r\n" +
								"%s",
								viewScript,
								scriptOf(currentModel)
							 );
			}
			
			if(currentModel.type() == DataModelType.AGGREGATED_MODEL) {
				currentModel = ((AggregatedModel)currentModel).model();
			} else {
				currentModel = null;
			}
		} while(currentModel != null);		
		
		viewScript = String.format(
				"(\r\n" +
				"	%s \r\n" + 
				") as %s",
				viewScript,
				new TableImpl(DataField.class).name()
			 );
		
		return model.base()
				    .select(DataField.class, viewScript);
	}
	
	@Override
	protected DataFields domainSetOf(final RecordSet<DataField> source) throws IOException {
		return new PgDataFields(source, model);
	}

	@Override
	public DataField get(String code) throws IOException {
		return where(DataField::code, code).first();
	}
	
	@Override
	protected DataField domainOf(final Record<DataField> record) throws IOException {
		return TypedDataField.convert(record);
	}

	@Override
	public EditableDataFields editables() throws IOException {
		return new PgEditableDataFields(model);
	}

	@Override
	public SimpleDataFields simples() throws IOException {
		return new PgSimpleDataFields(model);
	}

	@Override
	public ListDataFields lists() throws IOException {
		return new PgListDataFields(model);
	}

	@Override
	public TableDataFields tables() throws IOException {
		return new PgTableDataFields(model);
	}

	@Override
	public FormularDataFields formulars() throws IOException {
		return new PgFormularDataFields(model);
	}

	@Override
	public ParamDataFields params() throws IOException {
		return new PgParamDataFields(model);
	}

	@Override
	public Long add(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {
		
		if(model.type() == DataModelType.DATA_SHEET_MODEL) {
			new UserOf(this).profileOf(Supervisor.NAME).validateAccessibility("NEW_DATA_FIELD");
		}		

		source.isRequired(DataField::code, code); 		
		source.isRequired(DataField::name, name);
		
		source.mustCheckThisCondition(
			type != DataFieldType.NONE, 
			"Vous devez donner un type au champ !"
		); 
		
		source.mustCheckThisCondition(
			style != DataFieldStyle.NONE, 
			"Vous devez donner une forme au champ !"
		);
		
		source.mustBeUnique(DataField::code, code.toUpperCase(), DataField::model, model.id());	
		
		Record<DataField> record = source.entryOf(DataField::name, name)
										 .entryOf(DataField::code, code.toUpperCase())
										 .entryOf(DataField::type, type)
										 .entryOf(DataField::style, style)
										 .entryOf(DataField::description, description)
										 .entryOf(DataField::model, model.id())
										 .add(); 
		
		return record.id();
	}

	@Override
	public boolean contains(String code) throws IOException {
		return where(DataField::code, code).any();
	}

	@Override
	public EditableDataFields scalarEditableFields() throws IOException {
		return editables().where(EditableDataField::type, Matchers.notEqualsTo(DataFieldType.TABLE));
	}
	
	@Override
	public void remove(DataField item) throws IOException {
		
		if(item.code().equals("DATE"))
			return; // don't remove date field; It is used in an eventually aggregated model
		
		if(item.model().id().equals(model.id())) {
			
			if(model.type() == DataModelType.AGGREGATED_MODEL) {
				final AggregatedModel aModel = (AggregatedModel)model;				
				if(aModel.dateReference().id().equals(item.id())) {
					final DataField documentDate = aModel.fields().get("DATE");
					aModel.update(aModel.name(), documentDate); 
				}
			}

			super.remove(item); 
		}
	}
}
