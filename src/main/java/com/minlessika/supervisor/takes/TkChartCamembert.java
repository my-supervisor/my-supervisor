package com.minlessika.supervisor.takes;

import org.takes.rs.RsJson;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.indicator.ChartCamembert;
import com.minlessika.supervisor.indicator.impl.PxChartCamembert;
import com.minlessika.supervisor.xe.XeChartCamembert;

public final class TkChartCamembert extends TkBaseWrap {

	public TkChartCamembert(final Base base) {
		super(
				base, 
				req -> {
					final ChartCamembert indic = new PxChartCamembert(
													new DashboardIndicator(base, req)
												 );
					
					new RqComputedIndicator(indic, base, req).calculate();
					
					return new RsJson(
						new XeChartCamembert(indic) 
					);
				}
		);
	}	
}
