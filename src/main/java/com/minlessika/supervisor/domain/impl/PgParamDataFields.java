package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.ParamDataFields;

public final class PgParamDataFields extends DomainRecordables<ParamDataField, ParamDataFields> implements ParamDataFields {

	private final DataModel model;

	public PgParamDataFields(final DataModel model) throws IOException {
		this(viewSource(model), model);
	}
	
	private PgParamDataFields(final RecordSet<ParamDataField> source, final DataModel model) throws IOException {
		super(PxParamDataField.class, source);
		
		this.model = model;
		this.source = source.orderBy(ParamDataField::model, ParamDataField::id, OrderDirection.ASC);
	}
	
	private static String scriptOf(final DataModel model) {
		final Table table = new TableImpl(ParamDataField.class);
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
	
	private static RecordSet<ParamDataField> viewSource(final DataModel model) throws IOException{

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
				new TableImpl(ParamDataField.class).name()
			 );
		
		return model.base()
				    .select(ParamDataField.class, viewScript);
	}
	
	@Override
	protected ParamDataFields domainSetOf(final RecordSet<ParamDataField> source) throws IOException {
		return new PgParamDataFields(source, model);
	}
	
	@Override
	protected ParamDataField domainOf(final Record<ParamDataField> record) throws IOException {
		return new PxParamDataField(record.listOf(DataField.class).get(record.id()));
	}

	@Override
	public ParamDataField add(String code, String name, String value, DataFieldType type) throws IOException {
		
		if(type == DataFieldType.TABLE)
			throw new IllegalArgumentException("Un paramètre ne peut pas être de type table !");
		
		new DataFieldValueImpl(type, value).validate();
		
		final Long fieldId = new PgDataFields(model).add(code, name, type, DataFieldStyle.PARAMETER, StringUtils.EMPTY);		
		
		Record<ParamDataField> record = source.entryOf(ParamDataField::id, fieldId)	
											  .entryOf(ParamDataField::value, new DataFieldValueImpl(type, value).cleaned())
									          .add();
				
		return domainOf(record);
	}
	
	@Override
	public void remove(ParamDataField item) throws IOException{
		new PgDataFields(model).remove(item); 
	}

	@Override
	public ParamDataField get(String code) throws IOException {
		return where(ParamDataField::code, code).first();
	}
}
