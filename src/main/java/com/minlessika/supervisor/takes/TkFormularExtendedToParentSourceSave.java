package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToParentSourceSave extends TkBaseWrap {

	public TkFormularExtendedToParentSourceSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final Long modelId = Long.parseLong(form.single("model_id"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final Long referenceId = Long.parseLong(form.single("reference_id"));
					final ListDataField reference = model.fields().lists().get(referenceId); 
					
					final Long exprId = Long.parseLong(form.single("expr_id"));
					final FormularExtendedToParentExpression expr = (FormularExtendedToParentExpression)formular.expressions().get(exprId);
					
					final Long fieldId = Long.parseLong(form.single("parent_id"));
					
					final DataModel parentModel;
					final EditableDataField field;
					final FormularExtendedToParentSource itemSaved;
					
					final Long id = Long.parseLong(form.single("id", "0"));
					if(id > 0) {	
						itemSaved = expr.sources().get(id);
						parentModel = itemSaved.listSource().model();
						field = parentModel.fields().editables().get(fieldId);
						itemSaved.update(field);
					} else {
						final Long parentModelId = Long.parseLong(form.single("parent_model_id"));
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
