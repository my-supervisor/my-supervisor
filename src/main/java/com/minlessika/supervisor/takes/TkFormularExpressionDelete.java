package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExpressionDelete extends TkBaseWrap {

	public TkFormularExpressionDelete(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);

					final Smart href = new RqHref.Smart(req);
					final Long modelId = Long.parseLong(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId);
					
					final Long id = Long.parseLong(href.single("id"));
					
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
