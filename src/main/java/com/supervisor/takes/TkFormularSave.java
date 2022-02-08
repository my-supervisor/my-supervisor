package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularSave extends TkBaseWrap {

	public TkFormularSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final UUID modelId = UUID.fromString(form.single("model_id"));
					final String name = form.single("name");
					final DataFieldType type = DataFieldType.valueOf(form.single("type_id"));
					
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					final FormularDataField itemSaved;
					
					final OptUUID id = new OptUUID(form.single("id", "0"));
					if(id.isPresent()) {
						itemSaved = model.formulars().get(id.value());
						itemSaved.update(name, itemSaved.code(), type);
						
						String state = form.single("formular_condition_state", "");
						
						if(state.equals("added")) {
							Comparator comparator = Comparator.valueOf(form.single("formular_condition_comparator_id"));
							String value = form.single("formular_condition_value");
							double defaultValue = Double.parseDouble(form.single("formular_condition_default_value"));
							final String paramCode = form.single("formular_condition_code");
							ParamDataField param = model.params().where(ParamDataField::code, paramCode).first();
							
							itemSaved.addCondition(param, comparator, value, defaultValue);
						}else {
							itemSaved.removeCondition();
						}
						
						return new RsForward(
								new RsFlash(
					                "La formule a été modifiée avec succès !",
					                Level.FINE
					            ),
								String.format("/collect/aggregated-model/formular/edit?id=%s&model=%s", itemSaved.id(), model.id())
						    );
					} else {
						final CodeGenerator generator = new BasicCodeGenerator(
															model.fields(), 
															name
														);
						
						itemSaved = model.formulars().add(generator.generate(), name, type);
						
						return new RsForward(
							new RsFlash(
				                String.format("La formule %s a été créée avec succès !", itemSaved.code()),
				                Level.FINE
				            ),
							String.format("/collect/aggregated-model/formular/edit?id=%s&model=%s", itemSaved.id(), model.id())
					    );
					}
				}
		);
	}	
}
