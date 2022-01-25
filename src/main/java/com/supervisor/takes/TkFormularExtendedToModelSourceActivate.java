package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToModelSourceActivate extends TkBaseWrap {

	public TkFormularExtendedToModelSourceActivate(final Base base) {
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
					final FormularExtendedToModelExpression expr = (FormularExtendedToModelExpression)formular.expressions().get(exprId);
					
					final boolean active = Boolean.parseBoolean(href.single("active"));										
					
					final Long id = Long.parseLong(href.single("id"));
					final FormularExtendedToModelSource item = expr.sources().get(id);
					item.activate(active); 
	
					final String msg;
					if(active) {
						msg = "Source has been activated with success !";
					} else {
						msg = "Source has been deactivated with success !";
					}
					
					return new RsForward(
						new RsFlash(
								msg,
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/extended-to-model-expression/edit?id=%s&model=%s&formular=%s", expr.id(), model.id(), formular.id())
				    );
				}
		);
	}	
}
