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
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.UserDataModels;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.indicator.Indicator;

public final class TkIndicatorDelete extends TkBaseWrap {

	public TkIndicatorDelete(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));			
					if(id == 0)
						throw new IllegalArgumentException("Cet élément n'existe pas !");
					
					final Long activityId = Long.parseLong(StringUtils.replace(new RqHref.Smart(req).single("source"), "activity", ""));
					final Activity activity = module.activities().get(activityId);
					Indicator indicator = activity.indicators().get(id);

					if(new RqUser(base, req).notOwn(indicator)) {
						throw new IllegalArgumentException("Vous ne pouvez pas supprimer l'indicateur d'une activité partagée !");
					}

					// supprimer les modèles liés des règles d'exploitation pour un modèle d'indicateur
					if(indicator.isTemplate()) {
						
						UserDataModels models = module.dataModels();
						for (DataLink link : indicator.links().items()) {
							DataModel model = link.model();
							models.remove(model);
						}				
					}
					
					String name = indicator.name();
					activity.indicators().remove(indicator);
					
					return new RsForward(
						new RsFlash(
			                String.format("L'indicateur %s a été supprimé avec succès !", name),
			                Level.FINE
			            ),
						IndicatorSource.url(req)
				    );
				}
		);
	}	
}
