package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.indicator.Indicator;

public final class TkDataLinkDelete extends TkBaseWrap {

	public TkDataLinkDelete(Base base) {
		super(
				base, 
				req ->{
					final Supervisor module = new PxSupervisor(base, req);
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					final Long indicId = Long.parseLong(new RqHref.Smart(req).single("indic"));
					final String source = new RqHref.Smart(req).single("source");
					
					final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
					final Activity activity = module.activities().get(activityId);
					Indicator indic = activity.indicators().get(indicId);

					if(new RqUser(base, req).notOwn(indic)) {
						throw new IllegalArgumentException("Vous ne pouvez pas supprimer les liaisons d'un indicateur partagé !");
					}
					
					indic.links().remove(id);
								
					return new RsForward(
						new RsFlash(
								"La liaison a été supprimée avec succès !",
			                Level.FINE
			            ),
						String.format("/%s-setting/edit?id=%s&shortname=%s&source=%s", indic.type().shortName(), indic.id(), indic.type().shortName(), source)
				    );
				}
		);
	}
}
