package com.minlessika.supervisor.domain.impl;
import java.io.IOException;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.UserDataModels;
import com.minlessika.supervisor.indicator.impl.TypedDataModel;

public final class PgUserDataModels extends DomainRecordables<DataModel, UserDataModels> implements UserDataModels {

	private final User user;

	public PgUserDataModels(final User user) throws IOException {
		this(viewSource(user), user);
	}
	
	private PgUserDataModels(final RecordSet<DataModel> source, final User user) throws IOException {
		super(PxDataModel.class, source);
		
		this.user = user;
	}
	
	@Override
	protected DataModel domainOf(final Record<DataModel> record) throws IOException {
		return TypedDataModel.convert(record);
	}
	
	@Override
	protected UserDataModels domainSetOf(final RecordSet<DataModel> source) throws IOException {
		return new PgUserDataModels(source, user);
	}
	
	private static RecordSet<DataModel> viewSource(final User user) throws IOException {
		
		Table table = new TableImpl(DataModel.class);
		
		String viewScript = String.format(
								"(\r\n" + 
								"	select src.* \r\n" + 
								"   from %s src \r\n"+ 
								"	where src.owner_id = %s \r\n" +
								") as %s",
								table.name(),
								user.id(),
								table.name()
						    );
		
		return user.base()
				   .select(DataModel.class, viewScript);
	}
	
	@Override
	public void remove(DataModel item) throws IOException{
		new PgDataModels(user).remove(item);
	}
}
