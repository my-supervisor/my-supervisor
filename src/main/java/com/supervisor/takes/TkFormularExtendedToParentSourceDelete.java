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
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToParentSourceDelete extends TkBaseWrap {

	public TkFormularExtendedToParentSourceDelete(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);

					final Smart href = new RqHref.Smart(req);
					
					final UUID modelId = UUID.fromString(href.single("model"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID formularId = UUID.fromString(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final UUID exprId = UUID.fromString(href.single("expr"));
					final FormularExtendedToParentExpression expr = (FormularExtendedToParentExpression)formular.expressions().get(exprId);
					
					final UUID id = UUID.fromString(href.single("id"));
					
					final FormularExtendedToParentSource item = expr.sources().get(id);
					expr.sources().remove(item);
					
					return new RsForward(
						new RsFlash(
			                "La source a été supprimée avec succès !",
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/extended-to-parent-expression/edit?id=%s&model=%s&formular=%s", expr.id(), model.id(), formular.id())
				    );
				}
		);
	}	
}
