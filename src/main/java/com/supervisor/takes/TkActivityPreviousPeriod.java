package com.supervisor.takes;

import java.time.LocalDate;
import java.util.UUID;

import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.impl.BiPeriodOfPeriodicity;
import com.supervisor.domain.bi.impl.PreviousBiPeriod;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsJson;
import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XePeriod;

public final class TkActivityPreviousPeriod extends TkBaseWrap {

	public TkActivityPreviousPeriod(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					
					Smart params = new RqHref.Smart(req);					
					final LocalDate date = new RqDashboardDate(req).toLocalDate();
					
					UUID activityId = UUID.fromString(params.single("activity"));
					Activity activity = module.activities().get(activityId);
					final BiPeriod period = new PreviousBiPeriod(
						new BiPeriodOfPeriodicity(date, activity.periodicity()),
						activity.periodicity()
					);
					
					return new RsJson(
						new XePeriod(period) 
					);
				}
		);
	}	
}
