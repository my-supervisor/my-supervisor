package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToParentSourceDelete extends TkBaseWrap {

	public TkFormularExtendedToParentSourceDelete(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);

					final Smart href = new RqHref.Smart(req);
					
					final Long modelId = Long.parseLong(href.single("model"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final Long exprId = Long.parseLong(href.single("expr"));
					final FormularExtendedToParentExpression expr = (FormularExtendedToParentExpression)formular.expressions().get(exprId);
					
					final Long id = Long.parseLong(href.single("id"));
					
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
