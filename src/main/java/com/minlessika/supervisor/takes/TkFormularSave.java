package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.sdk.utils.BasicCodeGenerator;
import com.minlessika.sdk.utils.CodeGenerator;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularSave extends TkBaseWrap {

	public TkFormularSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final Long modelId = Long.parseLong(form.single("model_id"));
					final String name = form.single("name");
					final DataFieldType type = DataFieldType.valueOf(form.single("type_id"));
					
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					final FormularDataField itemSaved;
					
					final Long id = Long.parseLong(form.single("id", "0"));
					if(id > 0) {
						itemSaved = model.formulars().get(id);	
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
