package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToModelSourceSave extends TkBaseWrap {

	public TkFormularExtendedToModelSourceSave(final Base base) {
		super(
				base,
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final UUID modelId = UUID.fromString(form.single("model_id"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID modelToExtendId = UUID.fromString(form.single("model_to_extend_id"));
					final DataSheetModel modelToExtend = (DataSheetModel)module.dataModels().get(modelToExtendId); 
					
					final UUID formularId = UUID.fromString(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final UUID referenceId = UUID.fromString(form.single("reference_id"));
					final DataField reference = model.fields().get(referenceId); 
					
					final UUID exprId = UUID.fromString(form.single("expr_id"));
					final FormularExtendedToModelExpression expr = (FormularExtendedToModelExpression)formular.expressions().get(exprId);
					
					final UUID modelFieldId = UUID.fromString(form.single("model_field_id"));
					final EditableDataField modelField = modelToExtend.editableFields().get(modelFieldId);
					
					final UUID fieldToExtendId = UUID.fromString(form.single("field_to_extend_id"));
					final EditableDataField fieldToExtend = modelToExtend.editableFields().get(fieldToExtendId);
					
					final Comparator comparator = Comparator.valueOf(form.single("comparator_id"));
									
					final FormularExtendedToModelSource itemSaved;
					
					final OptUUID id = new OptUUID(form.single("id", "0"));
					if(id.isPresent()) {
						itemSaved = expr.sources().get(id.value());
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
