package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkWhenCaseSave extends TkBaseWrap {

	public TkWhenCaseSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final Smart href = new RqHref.Smart(new RqGreedy(req));		
					
					final Long modelId = Long.parseLong(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final Long expressionId = Long.parseLong(href.single("expression"));
					final FormularCaseExpression expression = (FormularCaseExpression)formular.expressions().get(expressionId);
					
					expression.cases().add();
					
					return new RsForward(
						new RsFlash(
			                "La possibilité a été créée avec succès !",
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/case-expression/edit?id=%s&model=%s&formular=%s", expression.id(), model.id(), formular.id())
				    );
				}
		);
	}	
}
