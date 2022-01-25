package com.supervisor.domain.impl;

import java.io.IOException;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.SharedResource;

public final class PgDataSheets extends DomainRecordables<DataSheet, DataSheets> implements DataSheets {

	private final User user;
	
	public PgDataSheets(final User user) throws IOException {
		this(viewSource(user), user);
	}
	
	private PgDataSheets(final RecordSet<DataSheet> source, final User user) {
		super(PxDataSheet.class, source);
		
		this.user = user;	
	}
	
	@Override
	protected DataSheets domainSetOf(final RecordSet<DataSheet> source) throws IOException {
		return new PgDataSheets(source, user);
	}
	
	private static RecordSet<DataSheet> viewSource(final User user) throws IOException{
		
		Table table = new TableImpl(DataSheet.class);
		Table tableModel = new TableImpl(DataSheetModel.class);
		Long ownerId = user.id();
		
		String viewScript = String.format(
					            "(\r\n" +
								"	select sheet.* from (\r\n" + 
								"		select * from %s \r\n" + 
								"		where owner_id = %s\r\n" + 
								"		UNION ALL\r\n" + 
								"		select sheet.* \r\n" + 
								"		from %s sheet\r\n" + 
								"		left join %s as model on model.id = sheet.model_id\r\n" + 
								"		left join %s res on res.resource_id = model.id\r\n" + 
								"		where res.type = 'DATA_SHEET_MODEL' and res.subscriber_id = %s and sheet.creator_id = %s\r\n" + 
								"	) as sheet \r\n" +
								"   left join %s as model on model.id = sheet.model_id \r\n" +
								"   where model.is_template = false \r\n" +
								") as %s \r\n",
								table.name(),
								ownerId,
								table.name(),
								tableModel.name(),
								new TableImpl(SharedResource.class).name(),
								ownerId,
								ownerId,
								new TableImpl(DataModel.class).name(),
								table.name()
							);
		
		return user.base()
				   .select(DataSheet.class, viewScript);
	}
}
