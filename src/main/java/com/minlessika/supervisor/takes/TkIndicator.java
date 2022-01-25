package com.minlessika.supervisor.takes;

import org.cactoos.iterable.Sticky;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.Indicators;
import com.minlessika.supervisor.xe.XeIndicator;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkIndicator extends TkBaseWrap {

	public TkIndicator(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final Long activityId = Long.parseLong(new RqHref.Smart(req).single("activity"));
					final Activity activity = module.activities().get(activityId);
					Indicators myItems = activity.indicators().where(Indicator::isTemplate, false);
					
					final XeSource xeSource;
					if(myItems.any())
						xeSource = new XeIndicator(myItems.items());
					else
						xeSource = XeSource.EMPTY;
					
					XeSource xeSupervisor = new XeSupervisor(module);
					
					return new RsPage(
							"/xsl/indicator.xsl",
							req,
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "indicators"),
									new XeAppend("submenu", "indicator"),
									xeSource,
									xeSupervisor
							)
					);
				}
		);
	}	
}
