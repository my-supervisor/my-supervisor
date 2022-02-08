package com.supervisor.takes;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.impl.IndicatorWrap;

public final class DashboardIndicator extends IndicatorWrap {

	public DashboardIndicator(Base base, Request req) throws IOException {
		super(indicatorOf(base, req));
	}
	
	private static Indicator indicatorOf(Base base, Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		Smart params = new RqHref.Smart(req);
		
		final UUID activityId = UUID.fromString(params.single("activity"));
		Activity activity = module.activities().get(activityId);
		
		final UUID id = UUID.fromString(params.single("id"));
		return activity.indicators().get(id);
	}
}
