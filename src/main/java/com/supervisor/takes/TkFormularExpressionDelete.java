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
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExpressionDelete extends TkBaseWrap {

	public TkFormularExpressionDelete(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);

					final Smart href = new RqHref.Smart(req);
					final UUID modelId = UUID.fromString(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID formularId = UUID.fromString(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId);
					
					final UUID id = UUID.fromString(href.single("id"));
					
					final FormularExpression item = formular.expressions().get(id);
					final String name = item.name();
					formular.expressions().remove(item);
					
					return new RsForward(
						new RsFlash(
			                String.format("L'expression %s a été supprimée avec succès !", name),
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/edit?id=%s&model=%s", formularId, model.id())
				    );
				}
		);
	}	
}
