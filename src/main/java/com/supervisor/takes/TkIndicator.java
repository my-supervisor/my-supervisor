package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.Indicators;
import com.supervisor.xe.XeIndicator;
import com.supervisor.xe.XeSupervisor;

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
