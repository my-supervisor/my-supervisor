package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataLinkParam;
import com.minlessika.supervisor.domain.DataLinkParams;
import com.minlessika.supervisor.domain.ParamDataField;

public final class PgDataLinkParams extends DomainRecordables<DataLinkParam, DataLinkParams> implements DataLinkParams {
	
	private final DataLink link;
	
	public PgDataLinkParams(final DataLink link) throws IOException {
		this(viewSource(link), link);
	}
	
	private PgDataLinkParams(final RecordSet<DataLinkParam> source, final DataLink link) throws IOException {
		super(PxDataLinkParam.class, source);
		
		this.link = link;
		this.source = source.where(DataLinkParam::link, link.id())
				            .orderBy(DataLinkParam::id);
	}
	
	@Override
	protected DataLinkParams domainSetOf(final RecordSet<DataLinkParam> source) throws IOException {
		return new PgDataLinkParams(source, link);
	}

	private static RecordSet<DataLinkParam> viewSource(final DataLink link) throws IOException {
		Table table = new TableImpl(DataLinkParam.class);
		
		String viewScript = String.format("(\r\n" + 
											"	select src.*, target.code, target.model_id, target.name \r\n" + 
					                        "   from %s as src \r\n" + 
											"	left join %s as target on target.id = src.id \r\n" +
										  ") as %s",
										table.name(),
										new TableImpl(DataField.class).name(),										
										table.name()
							);
		
		return link.base()
				   .select(DataLinkParam.class, viewScript);
	}
	
	@Override
	public DataLinkParam put(ParamDataField param, String value) throws IOException {
		
		DataLinkParam lparam = null;
		
		DataLinkParams lparams = this.where(DataLinkParam::id, param.id())
				                     .where(DataLinkParam::link, link.id());
		
		if(lparams.any()) {
			lparam = lparams.first();
			lparam.update(value); 
		}else {
			// créer le paramètre
			source.isRequired(DataLinkParam::value, value);
			
			if(!link.model().fields().params().contains(param))
				throw new IllegalArgumentException(String.format("Le paramètre %s ne figure pas parmi les paramètres du modèle agrégé %s !", param.name(), link.model().name()));
			
			new DataFieldValueImpl(param, value).validate();
			
			Record<DataLinkParam> record = source.entryOf(DataLinkParam::id, param.id())
											     .entryOf(DataLinkParam::link, link.id())
											     .entryOf(DataLinkParam::value, new DataFieldValueImpl(param, value).cleaned())
											     .add();

			lparam = domainOf(record);
		}
		
		return lparam;
	}
}
