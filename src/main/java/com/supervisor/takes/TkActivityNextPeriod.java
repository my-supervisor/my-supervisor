package com.supervisor.takes;

import java.time.LocalDate;

import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.impl.BiPeriodOfPeriodicity;
import com.supervisor.domain.bi.impl.NextBiPeriod;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsJson;
import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XePeriod;

public final class TkActivityNextPeriod extends TkBaseWrap {

	public TkActivityNextPeriod(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					
					Smart params = new RqHref.Smart(req);					
					final LocalDate date = new RqDashboardDate(req).toLocalDate();
					
					Long activityId = Long.parseLong(params.single("activity"));
					Activity activity = module.activities().get(activityId);
					final BiPeriod period = new NextBiPeriod(
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
