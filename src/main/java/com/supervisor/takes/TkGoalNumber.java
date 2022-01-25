package com.supervisor.takes;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rs.RsJson;

import com.supervisor.indicator.GoalNumber;
import com.supervisor.indicator.impl.PxGoalNumber;
import com.supervisor.xe.XeGoalNumber;

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
