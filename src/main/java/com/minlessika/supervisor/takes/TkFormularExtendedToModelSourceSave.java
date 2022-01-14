package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToModelSourceSave extends TkBaseWrap {

	public TkFormularExtendedToModelSourceSave(final Base base) {
		super(
				base,
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final Long modelId = Long.parseLong(form.single("model_id"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long modelToExtendId = Long.parseLong(form.single("model_to_extend_id"));
					final DataSheetModel modelToExtend = (DataSheetModel)module.dataModels().get(modelToExtendId); 
					
					final Long formularId = Long.parseLong(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final Long referenceId = Long.parseLong(form.single("reference_id"));
					final DataField reference = model.fields().get(referenceId); 
					
					final Long exprId = Long.parseLong(form.single("expr_id"));
					final FormularExtendedToModelExpression expr = (FormularExtendedToModelExpression)formular.expressions().get(exprId);
					
					final Long modelFieldId = Long.parseLong(form.single("model_field_id"));
					final EditableDataField modelField = modelToExtend.editableFields().get(modelFieldId);
					
					final Long fieldToExtendId = Long.parseLong(form.single("field_to_extend_id"));
					final EditableDataField fieldToExtend = modelToExtend.editableFields().get(fieldToExtendId);
					
					final Comparator comparator = Comparator.valueOf(form.single("comparator_id"));
									
					final FormularExtendedToModelSource itemSaved;
					
					final Long id = Long.parseLong(form.single("id", "0"));
					if(id > 0) {	
						itemSaved = expr.sources().get(id);
						itemSaved.update(modelField, comparator, reference, fieldToExtend);
					} else {
						itemSaved = expr.sources().add(modelToExtend, modelField, comparator, reference, fieldToExtend);
					}

					return new RsForward(
						new RsFlash(
			                "La source de l'expression a été enregistrée avec succès !",
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/extended-to-model-expression/edit?id=%s&model=%s&formular=%s", expr.id(), model.id(), formular.id())
				    );
				}
		);
	}	
}
