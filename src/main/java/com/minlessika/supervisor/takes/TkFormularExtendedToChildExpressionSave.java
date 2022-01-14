package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToChildExpression;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToChildExpressionSave extends TkBaseWrap {

	public TkFormularExtendedToChildExpressionSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final AggregateFunc aggregate = AggregateFunc.valueOf(form.single("aggregate_id"));
					final Long modelId = Long.parseLong(form.single("model_id"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long childModelId = Long.parseLong(form.single("child_model_id"));
					final DataSheetModel childModel = module.dataSheetModels().get(childModelId);
					
					final Long formularId = Long.parseLong(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final Long childId = Long.parseLong(form.single("child_id"));
					final EditableDataField child = childModel.fields().editables().get(childId); 
					
					final FormularExtendedToChildExpression itemSaved;
					
					final Long id = Long.parseLong(form.single("id", "0"));
					if(id > 0) {	
						itemSaved = (FormularExtendedToChildExpression)formular.expressions().get(id);		
					} else {
						itemSaved = formular.expressions().addChildExtension();
					}
					
					itemSaved.update(child, aggregate);
					
					return new RsForward(
							new RsFlash(
				                "L'expression a été enregistrée avec succès !",
				                Level.FINE
				            ),
							String.format("/collect/aggregated-model/formular/edit?id=%s&model=%s", formular.id(), model.id())
					    );
				}
		);
	}	
}
