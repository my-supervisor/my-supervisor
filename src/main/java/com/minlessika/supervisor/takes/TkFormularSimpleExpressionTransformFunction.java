package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.FormularFunc;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularSimpleExpression;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularSimpleExpressionTransformFunction extends TkBaseWrap {

	public TkFormularSimpleExpressionTransformFunction(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final Smart href = new RqHref.Smart(new RqGreedy(req));		
					
					final FormularFunc func = FormularFunc.valueOf(href.single("func"));
					
					final Long modelId = Long.parseLong(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final Long id = Long.parseLong(href.single("id"));
					final FormularSimpleExpression item = (FormularSimpleExpression)formular.expressions().get(id);
					
					item.update(func);
					
					return new RsForward(
						new RsFlash(
			                "La fonction de l'expression a été changée avec succès !",
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/simple-expression/edit?id=%s&model=%s&formular=%s", item.id(), model.id(), formular.id())
				    );
				}
		);
	}	
}
