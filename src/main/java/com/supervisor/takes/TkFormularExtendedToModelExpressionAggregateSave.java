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
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToModelExpressionAggregateSave extends TkBaseWrap {

	public TkFormularExtendedToModelExpressionAggregateSave(final Base base) {
		super(
				base,
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final AggregateFunc aggregate = AggregateFunc.valueOf(form.single("aggregate_id"));
					final UUID modelId = UUID.fromString(form.single("model_id"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID formularId = UUID.fromString(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId);  
					
					final FormularExtendedToModelExpression itemSaved;
					
					final UUID id = UUID.fromString(form.single("id"));
					itemSaved = (FormularExtendedToModelExpression)formular.expressions().get(id);
					
					itemSaved.update(aggregate); 
					
					return new RsForward(
							new RsFlash(
				                "La fonction aggrégat de l'expression a été modifiée avec succès !",
				                Level.FINE
				            ),
							String.format("/collect/aggregated-model/formular/extended-to-model-expression/edit?id=%s&model=%s&formular=%s", itemSaved.id(), model.id(), formular.id())
					    );
				}
		);
	}	
}
