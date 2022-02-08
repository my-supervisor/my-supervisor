package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularDelete extends TkBaseWrap {

	public TkFormularDelete(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);

					final Smart href = new RqHref.Smart(req);
					final UUID modelId = UUID.fromString(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID id = UUID.fromString(href.single("id"));
					final FormularDataField item = model.formulars().get(id);
					final String name = item.name();
					model.formulars().remove(item);
					
					return new RsForward(
						new RsFlash(
			                String.format("La formule %s a été supprimée avec succès !", name),
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/edit?id=%s", model.id())
				    );
				}
		);
	}	
}
