package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rs.RsJson;

import com.supervisor.indicator.NumberOriented;
import com.supervisor.indicator.impl.PxNumberOriented;
import com.supervisor.xe.XeNumberOriented;

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
