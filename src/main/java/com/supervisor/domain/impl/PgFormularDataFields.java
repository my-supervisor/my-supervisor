package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularDataFields;

public final class PgFormularDataFields extends DomainRecordables<FormularDataField, FormularDataFields> implements FormularDataFields {
	
	private final DataModel model;
	
	public PgFormularDataFields(final DataModel model) throws IOException {
		this(viewSource(model), model);
	}
	
	private PgFormularDataFields(final RecordSet<FormularDataField> source, final DataModel model) throws IOException {
		super(PxFormularDataField.class, source);
		
		this.model = model;
		this.source = source.orderBy(FormularDataField::model, FormularDataField::id, OrderDirection.ASC);
	}
	
	private static String scriptOf(final DataModel model) {
		final Table table = new TableImpl(FormularDataField.class);
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
	
	private static RecordSet<FormularDataField> viewSource(final DataModel model) throws IOException {
		
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
			}
		} while(currentModel.type() == DataModelType.AGGREGATED_MODEL);			
		
		viewScript = String.format(
				"(\r\n" +
				"	%s \r\n" + 
				") as %s",
				viewScript,
				new TableImpl(FormularDataField.class).name()
			 );
		
		return model.base()
				    .select(FormularDataField.class, viewScript);
	}
	
	@Override
	protected FormularDataFields domainSetOf(final RecordSet<FormularDataField> source) throws IOException {
		return new PgFormularDataFields(source, model);
	}
	
	@Override
	protected FormularDataField domainOf(final Record<FormularDataField> record) throws IOException {
		return new PxFormularDataField(record.listOf(DataField.class).get(record.id()));
	}
	
	@Override
	public FormularDataField add(String code, String name, DataFieldType type) throws IOException {
		
		if(type == DataFieldType.TABLE)
			throw new IllegalArgumentException("Formular data cannot be table !");
		
		final UUID fieldId = new PgDataFields(model).add(code, name, type, DataFieldStyle.FORMULAR, StringUtils.EMPTY);
		
		Record<FormularDataField> record = source.entryOf(FormularDataField::id, fieldId)	
										         .add();
		
		return domainOf(record);
	}

	@Override
	public FormularDataField get(String code) throws IOException {		
		return where(FormularDataField::code, code).first();
	}
	
	@Override
	public void remove(FormularDataField item) throws IOException{
		new PgDataFields(model).remove(item);	
	}
}
