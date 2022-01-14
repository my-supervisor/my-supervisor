package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.EditableDataFields;

public final class PgEditableDataFields extends DomainRecordables<EditableDataField, EditableDataFields> implements EditableDataFields {

	private final DataModel model;
	
	public PgEditableDataFields(final DataModel model) throws IOException {
		this(viewSource(model), model);
	}
	
	private PgEditableDataFields(final RecordSet<EditableDataField> source, final DataModel model) throws IOException {
		super(PxEditableDataField.class, source);
		
		this.model = model;
		this.source = source.orderBy(EditableDataField::order);
	}
	
	private static String scriptOf(final DataModel model) {
		final Table table = new TableImpl(EditableDataField.class);
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
	
	private static RecordSet<EditableDataField> viewSource(final DataModel model) throws IOException {
		
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
						new TableImpl(EditableDataField.class).name()
					 );
		
		return model.base()
				    .select(EditableDataField.class, viewScript);
	}
	
	@Override
	protected EditableDataFields domainSetOf(final RecordSet<EditableDataField> source) throws IOException {
		return new PgEditableDataFields(source, model);
	}
	
	@Override
	protected EditableDataField domainOf(final Record<EditableDataField> record) throws IOException {
		return (EditableDataField)TypedDataField.convert(record.listOf(DataField.class).get(record.id()));
	}

	@Override
	public EditableDataField get(String code) throws IOException {
		return where(EditableDataField::code, code).first();
	}

	@Override
	public EditableDataField add(String code, String name, DataFieldType type, DataFieldStyle style, UserScope userScope, boolean isMandatory, String description) throws IOException {
		
		final Long fieldId = new PgDataFields(model).add(code, name, type, style, description);
		
		Record<EditableDataField> record0 = source.entryOf(EditableDataField::id, fieldId)
												  .entryOf(EditableDataField::userScope, userScope)
												  .entryOf(EditableDataField::isMandatory, isMandatory)
												  .entryOf(EditableDataField::order, count() + 1)
												  .add(); 
		
		return domainOf(record0);
	}
	
	@Override
	public void remove(EditableDataField item) throws IOException{
		new PgDataFields(model).remove(item);		
	}
}
