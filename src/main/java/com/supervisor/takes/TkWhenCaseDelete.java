package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.WhenCase;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkWhenCaseDelete extends TkBaseWrap {

	public TkWhenCaseDelete(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);

					final Smart href = new RqHref.Smart(req);
					final Long modelId = Long.parseLong(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId);
					
					final Long expressionId = Long.parseLong(href.single("expression"));
					FormularCaseExpression expression = (FormularCaseExpression)formular.expressions().get(expressionId);
					
					final Long id = Long.parseLong(href.single("id"));		
					final WhenCase item = expression.cases().get(id);
					expression.cases().remove(item);
					
					return new RsForward(
						new RsFlash(
			                "La possibilité a été supprimée avec succès !",
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/case-expression/edit?id=%s&formular=%s&model=%s", expression.id(), formular.id(), model.id())
				    );
				}
		);
	}	
}
