package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.Supervisor;
import com.supervisor.indicator.BasePeriodicity;

public final class PgActivities extends DomainRecordables<Activity, Activities> implements Activities {

	private final User user;
	
	public PgActivities(final User user) throws IOException {
		super(PxActivity.class, viewSource(user));
		
		this.user = user;
	}
	
	public PgActivities(final RecordSet<Activity> source, final User user) {
		super(PxActivity.class, source);
		
		this.user = user;
	}
	
	@Override
	protected Activities domainSetOf(final RecordSet<Activity> source) throws IOException {
		return new PgActivities(source, user);
	}
	
	private static RecordSet<Activity> viewSource(final User user) throws IOException {
		
		final Table table = new TableImpl(Activity.class);
		final UUID ownerId = user.id();
		
		String viewScript = String.format("(\r\n" + 
							"	select * from %s \r\n" + 
							"	where owner_id = '%s'::uuid \r\n" +
							"	UNION ALL\r\n" + 
							"	select act.* \r\n" + 
							"	from %s act\r\n" + 
							"	left join %s res on res.resource_id = act.id\r\n" + 
							"	where res.type = 'ACTIVITY' and res.subscriber_id = '%s'::uuid\r\n" +
							") as %s",
							table.name(),
							ownerId,
							table.name(),
							new TableImpl(SharedResource.class).name(),
							ownerId,
							table.name()
				);
		
		return user.base()
				   .select(Activity.class, viewScript)
				   .orderBy(Activity::creationDate, OrderDirection.DESC);
	}

	@Override
	public Activity add(String name, String description) throws IOException {
		
		final User owner = new UserOf(this);
		
		user.profile().validateAccessibility("NEW_ACTIVITY", String.format("%s", count() + 1));
		
		source.isRequired(Activity::name, name);		
		
		Record<BasePeriodicity> periodicity = source.of(BasePeriodicity.class)
											        .entryOf(BasePeriodicity::number, 1)
											        .entryOf(BasePeriodicity::unit, PeriodicityUnit.DAILY)
											        .entryOf(BasePeriodicity::reference, LocalDate.of(2018, 1, 1))
											        .entryOf(BasePeriodicity::closeInterval, false)
											        .add();	
		
		Record<Activity> record = source.entryOf(Activity::name, name)
									    .entryOf(Activity::description, description)
									    .entryOf(Activity::defaultShown, false)
									    .entryOf(Activity::isTemplate, false)
									    .entryOf(Activity::periodicity, periodicity.id())
									    .entryOf(Activity::version, "1.0.0.0")
									    .add();
		
		return domainOf(record);
	}

	@Override
	public Activities ownActivities() throws IOException {
		return where(Activity::ownerId, base().currentUserId());
	}
	
	@Override
	public void remove(Activity item) throws IOException {		
		
		item.sharing().remove();			
		item.indicators().remove();
		
		// remove all interacting models from receivers
		new PgUserAggregatedModels(new OwnerOf(item))
				.where(AggregatedModel::interacting, true)
				.where(AggregatedModel::coreActivity, item.id())
				.remove();
		
		super.remove(item);
	}
}
