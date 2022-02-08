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

public final class RqComputedIndicator implements ComputedIndicator {

	private final Indicator origin;
	private final Request req;
	private final Base base;
	
	public RqComputedIndicator(Indicator origin, Base base, Request req) {
		this.origin = origin;
		this.req = req;
		this.base = base;
	}
	
	@Override
	public void calculate() throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		Smart params = new RqHref.Smart(req);
		final UUID activityId = UUID.fromString(params.single("activity"));
		Activity activity = module.activities().get(activityId);
		
		origin.calculate(new RqDashboardDate(req).toLocalDate(), activity);
	}
}
