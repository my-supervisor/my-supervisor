package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.FormularFunc;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularSimpleExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularSimpleExpressionSave extends TkBaseWrap {

	public TkFormularSimpleExpressionSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final FormularFunc func = FormularFunc.valueOf(form.single("func_id"));
					
					final UUID modelId = UUID.fromString(form.single("model_id"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID formularId = UUID.fromString(form.single("formular_id"));
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
