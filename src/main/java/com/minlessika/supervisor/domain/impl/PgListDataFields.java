package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.OrderDirection;
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
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFields;

public final class PgListDataFields extends DomainRecordables<ListDataField, ListDataFields> implements ListDataFields {

	private final DataModel model;
	
	public PgListDataFields(final DataModel model) throws IOException {
		this(viewSource(model), model);
	}
	
	private PgListDataFields(final RecordSet<ListDataField> source, final DataModel model) throws IOException {
		super(PxListDataField.class, source);
		
		this.model = model;
		this.source = source.where(ListDataField::style, DataFieldStyle.LIST)
							.orderBy(ListDataField::id);
	}
	
	private static String scriptOf(final DataModel model) {
		final Table table = new TableImpl(ListDataField.class);
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
	
	private static RecordSet<ListDataField> viewSource(final DataModel model) throws IOException{
		
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
						new TableImpl(ListDataField.class).name()
					 );
		
		return model.base()
				    .select(ListDataField.class, viewScript);
	}
	
	@Override
	protected ListDataFields domainSetOf(final RecordSet<ListDataField> source) throws IOException {
		return new PgListDataFields(source, model);
	}
	
	@Override
	protected ListDataField domainOf(final Record<ListDataField> record) throws IOException {
		return new PxListDataField(record.listOf(DataField.class).get(record.id()));
	}

	@Override
	public ListDataField get(String code) throws IOException {
		return where(ListDataField::code, code).first();
	}

	@Override
	public ListDataField add(String code, String name, DataFieldType type, boolean isMandatory, String description) throws IOException {
		
		if(model.type() != DataModelType.DATA_SHEET_MODEL)
			throw new IllegalArgumentException("Vous ne pouvez ajouter un champ liste qu'à un modèle de feuille de données !");
		
		final EditableDataField field = new PgEditableDataFields(model).add(code, name, type, DataFieldStyle.LIST, UserScope.USER, isMandatory, description);
		final ListDataField item = (ListDataField)field;
		item.update(OrderDirection.ASC, 5);
		item.makeReadOnly(false);
		item.makeMandatory(isMandatory);
		item.makeMustEditOnce(false);
		item.makeCanBeUpdated(true);
		
		return item;
	}
	
	@Override
	public void remove(ListDataField item) throws IOException {
		new PgEditableDataFields(model).remove(item);
	}
}
