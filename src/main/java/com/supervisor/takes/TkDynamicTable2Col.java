package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rs.RsJson;

import com.supervisor.indicator.DynamicTable2Col;
import com.supervisor.indicator.impl.PxDynamicTable2Col;
import com.supervisor.xe.XeDynamicTable2Col;

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
