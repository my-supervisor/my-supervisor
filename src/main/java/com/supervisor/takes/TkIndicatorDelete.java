package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.supervisor.domain.Activity;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.UserDataModels;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.Indicator;

public final class TkIndicatorDelete extends TkBaseWrap {

	public TkIndicatorDelete(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(!id.isPresent())
						throw new IllegalArgumentException("Cet élément n'existe pas !");
					
					final UUID activityId = UUID.fromString(StringUtils.replace(new RqHref.Smart(req).single("source"), "activity", ""));
					final Activity activity = module.activities().get(activityId);
					Indicator indicator = activity.indicators().get(id.get());

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
