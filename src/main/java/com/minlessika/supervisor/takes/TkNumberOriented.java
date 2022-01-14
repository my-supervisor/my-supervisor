package com.minlessika.supervisor.takes;

import org.takes.rs.RsJson;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.indicator.NumberOriented;
import com.minlessika.supervisor.indicator.impl.PxNumberOriented;
import com.minlessika.supervisor.xe.XeNumberOriented;

public final class TkNumberOriented extends TkBaseWrap {

	public TkNumberOriented(final Base base) {
		super(
				base, 
				req -> {
					final NumberOriented indic = new PxNumberOriented(
													new DashboardIndicator(base, req)
												 );
					
					new RqComputedIndicator(indic, base, req).calculate();
					
					return new RsJson(
							new XeNumberOriented(indic) 
					);
				}
		);
	}	
}
