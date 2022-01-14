package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.UserAggregatedModels;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkAggregatedModelDelete extends TkBaseWrap {

	public TkAggregatedModelDelete(Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final UserAggregatedModels items = module.aggregatedModels();
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));

					final AggregatedModel item = items.get(id);
					if(new RqUser(base, req).notOwn(item)) {
						throw new IllegalArgumentException("Vous ne pouvez pas supprimer ce modèle !");
					}
					
					String name = item.name();
					items.remove(item);
								
					return new RsForward(
						new RsFlash(
			                String.format("Le modèle agrégé %s a été supprimé avec succès !", name),
			                Level.FINE
			            ),
			            "/collect/aggregated-model"
				    );
				}
		);
	}
}
