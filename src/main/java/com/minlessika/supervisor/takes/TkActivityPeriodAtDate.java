package com.minlessika.supervisor.takes;

import java.time.LocalDate;

import com.minlessika.supervisor.domain.bi.BiPeriod;
import com.minlessika.supervisor.domain.bi.impl.BiPeriodOfPeriodicity;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsJson;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XePeriod;

public final class TkActivityPeriodAtDate extends TkBaseWrap {

	public TkActivityPeriodAtDate(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					
					Smart params = new RqHref.Smart(req);					
					final LocalDate date = new RqDashboardDate(req).toLocalDate();
					
					Long activityId = Long.parseLong(params.single("activity"));
					Activity activity = module.activities().get(activityId);
					final BiPeriod period = new BiPeriodOfPeriodicity(date, activity.periodicity());
					
					return new RsJson(
						new XePeriod(period) 
					);
				}
		);
	}	
}
