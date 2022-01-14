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
import com.minlessika.supervisor.xe.XeActivity;
import com.minlessika.supervisor.xe.XeIndicatorType;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkIndicatorType extends TkBaseWrap {

	public TkIndicatorType(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					Long activityId = Long.parseLong(new RqHref.Smart(req).single("activity", "0"));
					if(activityId == 0)
						throw new IllegalArgumentException("Vous devez spécifier l'activité pour lequel vous créer cet indicateur !");
					
					Activity activity = module.activities()
							                  .get(activityId);
					
					XeSource xeActivity = new XeActivity(activity);
					XeSource xeSupervisor = new XeSupervisor(module);
					XeSource xeIndicatorTypes = new XeIndicatorType(module.indicatorTypes());
					XeSource xeSource = new XeAppend("source", new RqHref.Smart(req).single("source"));
					
					return new RsPage(
							"/com/supervisor/xsl/indicators/indicator_type.xsl",
							req,
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "activities"),
									xeIndicatorTypes,
									xeSupervisor,
									xeActivity,
									xeSource
							)
					);
				}
		);
	}	
}
