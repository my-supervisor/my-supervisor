package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.UserAggregatedModels;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;

public final class PgUserAggregatedModels extends DomainRecordables<AggregatedModel, UserAggregatedModels> implements UserAggregatedModels {

	private final User user;
	
	public PgUserAggregatedModels(final User user) throws IOException {
		this(viewSource(user), user);
	}
	
	private PgUserAggregatedModels(final RecordSet<AggregatedModel> source, final User user) {
		super(PxAggregatedModel.class, source);
		
		this.user = user;
	}
	
	@Override
	protected UserAggregatedModels domainSetOf(final RecordSet<AggregatedModel> source) throws IOException {
		return new PgUserAggregatedModels(source, user);
	}
	
	private static RecordSet<AggregatedModel> viewSource(final User user) throws IOException {
		
		Table tableModel = new TableImpl(AggregatedModel.class);
		final UUID ownerId = user.id();
		
		String viewScript = String.format(
								"(\r\n" + 
								"	select am.*, dmodel.activity_id as core_activity_id, core.interacting, core.code, core.type, core.active, core.activity_id, core.name, core.description, core.is_template \r\n" + 
								"   from %s am \r\n" +
								"   left join %s model on model.id = am.model_id \r\n" +
								"   left join %s dmodel on dmodel.id = model.id \r\n" + 
								"   left join %s core on core.id = am.id \r\n" +
								"	where am.owner_id = %s\r\n" + 
								") as %s",
								tableModel.name(),
								new TableImpl(DataSheetModel.class).name(),
								new TableImpl(DataModel.class).name(),
								new TableImpl(DataModel.class).name(),
								ownerId,
								tableModel.name()
							);
		
		return user.base()
				   .select(AggregatedModel.class, viewScript)
				   .orderBy(AggregatedModel::id, OrderDirection.DESC);
	}
	
	@Override
	public void remove(AggregatedModel item) throws IOException {
		new PgUserDataModels(user).remove(item);
	}
	
	@Override
	public boolean contains(String code) throws IOException {
		return where(AggregatedModel::code, code).any();
	}
}
