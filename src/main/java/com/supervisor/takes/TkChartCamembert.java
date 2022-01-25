package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rs.RsJson;

import com.supervisor.indicator.ChartCamembert;
import com.supervisor.indicator.impl.PxChartCamembert;
import com.supervisor.xe.XeChartCamembert;

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
