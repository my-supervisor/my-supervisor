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
import com.supervisor.domain.DataModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToParentSourceSave extends TkBaseWrap {

	public TkFormularExtendedToParentSourceSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final UUID modelId = UUID.fromString(form.single("model_id"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID formularId = UUID.fromString(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final UUID referenceId = UUID.fromString(form.single("reference_id"));
					final ListDataField reference = model.fields().lists().get(referenceId); 
					
					final UUID exprId = UUID.fromString(form.single("expr_id"));
					final FormularExtendedToParentExpression expr = (FormularExtendedToParentExpression)formular.expressions().get(exprId);
					
					final UUID fieldId = UUID.fromString(form.single("parent_id"));
					
					final DataModel parentModel;
					final EditableDataField field;
					final FormularExtendedToParentSource itemSaved;
					
					final OptUUID id = new OptUUID(form.single("id", "0"));
					if(id.isPresent()) {
						itemSaved = expr.sources().get(id.get());
						parentModel = itemSaved.listSource().model();
						field = parentModel.fields().editables().get(fieldId);
						itemSaved.update(field);
					} else {
						final UUID parentModelId = UUID.fromString(form.single("parent_model_id"));
						parentModel = model.coreModel().parents().get(parentModelId); 
						field = parentModel.fields().editables().get(fieldId);
						final ListDataFieldSource src = reference.sources().whichBasedOn(parentModel);
						itemSaved = expr.sources().add(src, field);
					}

					return new RsForward(
						new RsFlash(
			                "L'expression a été enregistrée avec succès !",
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/extended-to-parent-expression/edit?id=%s&model=%s&formular=%s", expr.id(), model.id(), formular.id())
				    );
				}
		);
	}	
}
