package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.sdk.utils.BasicCodeGenerator;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.TableDataFields;

public final class PgTableDataFields extends DomainRecordables<TableDataField, TableDataFields> implements TableDataFields {

	private final DataModel model;
	
	public PgTableDataFields(final DataModel model) throws IOException {
		this(viewSource(model), model);
	}
	
	private PgTableDataFields(final RecordSet<TableDataField> source, final DataModel model) throws IOException {
		super(PxTableDataField.class, source);
		
		this.model = model;
		this.source = source.where(TableDataField::style, DataFieldStyle.STRUCTURE)
							.orderBy(TableDataField::id);
	}
	
	private static String scriptOf(final DataModel model) {
		final Table table = new TableImpl(TableDataField.class);
		return String.format(
					"select src.*, target.code, target.model_id, target.name, target.description, target.type, target.style \r\n" + 
		            "from %s as src \r\n" + 
		            "left join %s as target on target.id = src.id \r\n" +
					"where target.model_id = %s", 
					table.name(),
					new TableImpl(DataField.class).name(),										
					model.id()
				);
	}
	
	private static RecordSet<TableDataField> viewSource(final DataModel model) throws IOException{
		
		String viewScript;
		if(model.type() == DataModelType.DATA_SHEET_MODEL) {
			viewScript = scriptOf(model);
		} else {
			viewScript = scriptOf(((AggregatedModel)model).coreModel());
		}

		viewScript = String.format(
						"(\r\n" +
						"	%s \r\n" + 
						") as %s",
						viewScript,
						new TableImpl(TableDataField.class).name()
					 );
		
		return model.base()
				    .select(TableDataField.class, viewScript);
	}
	
	@Override
	protected TableDataFields domainSetOf(final RecordSet<TableDataField> source) throws IOException {
		return new PgTableDataFields(source, model);
	}
	
	@Override
	protected TableDataField domainOf(final Record<TableDataField> record) throws IOException {
		return new PxTableDataField(record.listOf(DataField.class).get(record.id()));
	}

	@Override
	public TableDataField get(String code) throws IOException {
		return where(TableDataField::code, code).first();
	}

	@Override
	public TableDataField add(String code, String name, boolean isMandatory, String description) throws IOException {
		
		if(model.type() != DataModelType.DATA_SHEET_MODEL)
			throw new IllegalArgumentException("Vous ne pouvez ajouter une table qu'à un modèle de feuille de données !");
		
		new UserOf(this).currentProfile().validateAccessibility("NEW_DATA_FIELD_TABLE", String.format("%s", model.fields().where(DataField::type, DataFieldType.TABLE).count() + 1));
		
		final EditableDataField item = new PgEditableDataFields(model).add(code, name, DataFieldType.TABLE, DataFieldStyle.STRUCTURE, UserScope.USER, isMandatory, description);
		final String structureCode = new BasicCodeGenerator(
										model.activity().dataModels(), 
										code
								     ).generate();
		
		final DataSheetModel structure = model.activity().forms().add(structureCode, name, description);
		
		source.of(TableDataField.class)
		      .get(item.id())
			  .entryOf(TableDataField::structure, structure.id())
			  .update();
		
		final ListDataField parentField = structure.fields().lists().add("PARENT", model.name(), DataFieldType.STRING, true, StringUtils.EMPTY);
		source.of(ListDataField.class)
		      .get(parentField.id())
		      .entryOf(ListDataField::userScope, UserScope.SYSTEM)
		      .update();
		
		parentField.order(11);
		parentField.makeMandatory(isMandatory);
		parentField.makeMustEditOnce(false);		
		
		parentField.update(OrderDirection.DESC, 5);
		parentField.sources().add(model, model.fields().get("REF"), model.fields().get("DATE"));
		
		return (TableDataField)item;
	}
	
	@Override
	public void remove(TableDataField item) throws IOException {
		if(item.model().id().equals(model.id())) {
			final DataSheetModel structure = item.structure();
			new PgEditableDataFields(model).remove(item);
			new PgDataSheetModels(new OwnerOf(structure)).remove(structure);
		}		
	}
}
