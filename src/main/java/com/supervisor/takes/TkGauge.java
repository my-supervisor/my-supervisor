package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rs.RsJson;

import com.supervisor.indicator.Gauge;
import com.supervisor.indicator.impl.PxGauge;
import com.supervisor.xe.XeGauge;

public final class TkGauge extends TkBaseWrap {

	public TkGauge(final Base base) {
		super(
				base, 
				req -> {
					final Gauge indic = new PxGauge(
											new DashboardIndicator(base, req)
									 	);
					
					new RqComputedIndicator(indic, base, req).calculate();
					
					return new RsJson(
						new XeGauge(indic) 
					);
				}
		);
	}	
}
