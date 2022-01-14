package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetOfModels;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.SimpleDataField;
import com.minlessika.supervisor.domain.SimpleDataFields;

public final class PgSimpleDataFields extends DomainRecordables<SimpleDataField, SimpleDataFields> implements SimpleDataFields {

	private final DataModel model;
	
	public PgSimpleDataFields(final DataModel model) throws IOException {
		this(viewSource(model), model);
	}
	
	private PgSimpleDataFields(final RecordSet<SimpleDataField> source, final DataModel model) throws IOException {
		super(PxSimpleDataField.class, source);
		
		this.model = model;
		this.source = source.where(SimpleDataField::style, DataFieldStyle.SIMPLE)
							.orderBy(SimpleDataField::id);
	}
	
	private static String scriptOf(final DataModel model) {
		final Table table = new TableImpl(SimpleDataField.class);
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
	
	private static RecordSet<SimpleDataField> viewSource(final DataModel model) throws IOException{
		
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
						new TableImpl(SimpleDataField.class).name()
					 );
		
		return model.base()
				    .select(SimpleDataField.class, viewScript);
	}
	
	@Override
	protected SimpleDataFields domainSetOf(final RecordSet<SimpleDataField> source) throws IOException {
		return new PgSimpleDataFields(source, model);
	}
	
	@Override
	protected SimpleDataField domainOf(final Record<SimpleDataField> record) throws IOException {
		return new PxSimpleDataField(record.listOf(DataField.class).get(record.id()));
	}

	@Override
	public SimpleDataField get(String code) throws IOException {
		return where(SimpleDataField::code, code).first();
	}

	@Override
	public SimpleDataField add(String code, String name, DataFieldType type, boolean isMandatory, String defaultValue, String description) throws IOException {
		
		if(model.type() != DataModelType.DATA_SHEET_MODEL)
			throw new IllegalArgumentException("Vous ne pouvez ajouter un champ simple qu'à un modèle de feuille de données !");
		
		if(type == DataFieldType.TABLE)
			throw new IllegalArgumentException("Simple data waited !");
		
		final DataSheetOfModels sheets = new PxDataSheetModel(model).sheets();
		source.mustCheckThisCondition(
			!isMandatory || sheets.isEmpty() || StringUtils.isNotBlank(defaultValue), 
			"Champ obligatoire : vous devez spécifier une valeur par défaut !"
		);
		
		final EditableDataField field = new PgEditableDataFields(model).add(code, name, type, DataFieldStyle.SIMPLE, UserScope.USER, isMandatory, description);
		final SimpleDataField item = (SimpleDataField)field;
		item.makeReadOnly(false);
		item.makeMandatory(isMandatory);
		item.makeMustEditOnce(false);
		item.takeLastValue(false);
		item.defaultValue(defaultValue);
		
		if(sheets.isEmpty())
			return item;

		base().update(
				  String.format(
						"insert into %s \r\n"
					  + "(value, sheet_id, origin_field_id, guid, creation_date, creator_id, last_modification_date, last_modifier_id, owner_id, tag) \r\n"
					  + "(\r\n"
					  + "	select ?, sheet.id, ?, uuid_in((md5((random())::text))::cstring), now(), sheet.creator_id, now(), sheet.last_modifier_id, sheet.owner_id, NULL \r\n"
					  + "   from %s as sheet \r\n"
					  + "   where sheet.model_id = ? \r\n"
					  + ");",
					  new TableImpl(DataFieldOfSheet.class).name(),
					  new TableImpl(DataSheet.class).name()
				  ),			  
				  Arrays.asList(item.defaultValue(), item.id(), model.id())
		    );
		
		return item;
	}
	
	@Override
	public void remove(SimpleDataField item) throws IOException{
		new PgEditableDataFields(model).remove(item);
	}
}
