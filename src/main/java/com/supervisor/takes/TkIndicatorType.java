package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.cactoos.iterable.Sticky;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivity;
import com.supervisor.xe.XeIndicatorType;
import com.supervisor.xe.XeSupervisor;

public final class TkIndicatorType extends TkBaseWrap {

	public TkIndicatorType(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					OptUUID activityId = new OptUUID(new RqHref.Smart(req).single("activity", "0"));
					if(activityId.isEmpty())
						throw new IllegalArgumentException("Vous devez spécifier l'activité pour lequel vous créer cet indicateur !");
					
					Activity activity = module.activities()
							                  .get(activityId.value());
					
					XeSource xeActivity = new XeActivity(activity);
					XeSource xeSupervisor = new XeSupervisor(module);
					XeSource xeIndicatorTypes = new XeIndicatorType(module.indicatorTypes());
					XeSource xeSource = new XeAppend("source", new RqHref.Smart(req).single("source"));
					
					return new RsPage(
							"/xsl/indicators/indicator_type.xsl",
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
