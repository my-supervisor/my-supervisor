package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BasicModule;
import com.supervisor.sdk.datasource.RecordSet;
import org.takes.Request;

import com.supervisor.domain.Membership;
import com.supervisor.domain.Profiles;
import com.supervisor.domain.User;
import com.supervisor.takes.RqUser;
import com.supervisor.domain.Activities;
import com.supervisor.domain.ActivityNotification;
import com.supervisor.domain.ActivityTemplates;
import com.supervisor.domain.ActivityTemplatesPublished;
import com.supervisor.domain.DataSheetModels;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.Resources;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.Sharing;
import com.supervisor.domain.Subscription;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.UserAggregatedModels;
import com.supervisor.domain.UserDataModels;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.IndicatorTypes;
import com.supervisor.indicator.impl.PxIndicatorTypes;

public final class PxSupervisor extends BasicModule implements Supervisor {

	private final RecordSet<User> source;
	private final User user;
	private final Base base;
	
	public PxSupervisor(final Base base, final Request req) throws IOException {
		this(base, new RqUser(base, req));
	}
		
	public PxSupervisor(final Base base, final User user) throws IOException {
		super(Supervisor.NAME, base.appInfo());	
		this.base = base;
		this.base.changeUser(user.id());
		this.source = this.base.select(User.class);
		this.user = new DmUser(this.source.get(user.id())); 
	}
	
	@Override
	public Activities activities() throws IOException {
		return new PgActivities(user);
	}

	@Override
	public DataSheetModels dataSheetModels() throws IOException {
		return new PgDataSheetModels(user);
	}

	@Override
	public DataSheets dataSheets() throws IOException {
		return new PgDataSheets(user); 
	}

	@Override
	public ActivityNotification activityNotification() throws IOException {
		return new ActivityNotificationImpl(
					source.base().wsServer()
			  ); 
	}

	@Override
	public Sharing sharing() throws IOException {
		return new PxSharing(
					source.of(SharedResource.class)
			   );
	}

	@Override
	public Subscription subscription() throws IOException {
		return new PxSubscription(
					source.of(SharedResource.class)
			   );
	}

	@Override
	public Resources resources() throws IOException {
		return new ResourcesImpl(
					source.of(SharedResource.class),
					user
				);
	}

	@Override
	public Membership membership() throws IOException {
		return new DmMembership(
					source.base(), 
					user
				);
	}

	@Override
	public IndicatorTypes indicatorTypes() throws IOException {
		return new PxIndicatorTypes(
					source.of(IndicatorType.class)
				);
	}

	@Override
	public UserAggregatedModels aggregatedModels() throws IOException {
		return new PgUserAggregatedModels(user);
	}

	@Override
	public ActivityTemplatesPublished activityTemplatesPublished() throws IOException {
		return new PgActivityTemplatesPublished(user);
	}

	@Override
	public ActivityTemplates activityTemplates() throws IOException {
		return new PgActivityTemplates(user);
	}

	@Override
	public User user() {
		return user;
	}

	@Override
	public UserDataModels dataModels() throws IOException {
		return new PgUserDataModels(user);
	}	
	
	@Override
	public Profiles profiles() throws IOException {
		return new PxProfiles(base, NAME);
	}
}
