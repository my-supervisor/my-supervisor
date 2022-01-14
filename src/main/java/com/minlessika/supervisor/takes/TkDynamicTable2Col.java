package com.minlessika.supervisor.takes;

import org.takes.rs.RsJson;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.indicator.DynamicTable2Col;
import com.minlessika.supervisor.indicator.impl.PxDynamicTable2Col;
import com.minlessika.supervisor.xe.XeDynamicTable2Col;

public final class TkDynamicTable2Col extends TkBaseWrap {

	public TkDynamicTable2Col(final Base base) {
		super(
				base, 
				req -> {
					final DynamicTable2Col indic = new PxDynamicTable2Col(
														new DashboardIndicator(base, req)
												   );
					
					new RqComputedIndicator(indic, base, req).calculate();
					
					return new RsJson(
						new XeDynamicTable2Col(indic) 
					);
				}
		);
	}	
}
