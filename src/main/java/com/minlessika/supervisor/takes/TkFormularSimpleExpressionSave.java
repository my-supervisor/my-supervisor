package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.FormularFunc;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularSimpleExpression;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularSimpleExpressionSave extends TkBaseWrap {

	public TkFormularSimpleExpressionSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final FormularFunc func = FormularFunc.valueOf(form.single("func_id"));
					
					final Long modelId = Long.parseLong(form.single("model_id"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final FormularSimpleExpression itemSaved;
					
					final Long id = Long.parseLong(form.single("id", "0"));
					if(id > 0) {
						
						return new RsForward(
								new RsFlash(
					                "L'expression a été modifiée avec succès !",
					                Level.FINE
					            ),
								String.format("/collect/aggregated-model/formular/edit?id=%s&model=%s", formular.id(), model.id())
						    );
					}else
					{
						itemSaved = formular.expressions().add(func);
						
						return new RsForward(
							new RsFlash(
				                String.format("L'expression %s a été créée avec succès !", itemSaved.name()),
				                Level.FINE
				            ),
							String.format("/collect/aggregated-model/formular/simple-expression/edit?id=%s&model=%s&formular=%s", itemSaved.id(), model.id(), formular.id())
					    );
					}
				}
		);
	}	
}
