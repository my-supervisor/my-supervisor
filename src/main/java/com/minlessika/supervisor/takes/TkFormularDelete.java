package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularDelete extends TkBaseWrap {

	public TkFormularDelete(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);

					final Smart href = new RqHref.Smart(req);
					final Long modelId = Long.parseLong(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long id = Long.parseLong(href.single("id"));
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
