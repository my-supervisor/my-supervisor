package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToChildExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToChildExpressionSave extends TkBaseWrap {

	public TkFormularExtendedToChildExpressionSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final AggregateFunc aggregate = AggregateFunc.valueOf(form.single("aggregate_id"));
					final UUID modelId = UUID.fromString(form.single("model_id"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID childModelId = UUID.fromString(form.single("child_model_id"));
					final DataSheetModel childModel = module.dataSheetModels().get(childModelId);
					
					final UUID formularId = UUID.fromString(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final UUID childId = UUID.fromString(form.single("child_id"));
					final EditableDataField child = childModel.fields().editables().get(childId); 
					
					final FormularExtendedToChildExpression itemSaved;
					
					final OptUUID id = new OptUUID(form.single("id", "0"));
					if(id.isPresent()) {
						itemSaved = (FormularExtendedToChildExpression)formular.expressions().get(id.get());
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
