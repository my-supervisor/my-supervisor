package com.minlessika.supervisor.takes;
import org.takes.rs.RsJson;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.indicator.GoalNumber;
import com.minlessika.supervisor.indicator.impl.PxGoalNumber;
import com.minlessika.supervisor.xe.XeGoalNumber;

public final class TkGoalNumber extends TkBaseWrap {

	public TkGoalNumber(final Base base) {
		super(
				base, 
				req -> {
					final GoalNumber indic = new PxGoalNumber(
												new DashboardIndicator(base, req)
											 );
					
					new RqComputedIndicator(indic, base, req).calculate();
					
					return new RsJson(
							new XeGoalNumber(indic) 
					);
				}
		);
	}	
}
