package com.minlessika.supervisor.takes;

import org.takes.rs.RsJson;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.indicator.Gauge;
import com.minlessika.supervisor.indicator.impl.PxGauge;
import com.minlessika.supervisor.xe.XeGauge;

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
