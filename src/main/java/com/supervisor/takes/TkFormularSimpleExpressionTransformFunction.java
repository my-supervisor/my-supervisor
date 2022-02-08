package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.FormularFunc;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularSimpleExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularSimpleExpressionTransformFunction extends TkBaseWrap {

	public TkFormularSimpleExpressionTransformFunction(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final Smart href = new RqHref.Smart(new RqGreedy(req));		
					
					final FormularFunc func = FormularFunc.valueOf(href.single("func"));
					
					final UUID modelId = UUID.fromString(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID formularId = UUID.fromString(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final UUID id = UUID.fromString(href.single("id"));
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
