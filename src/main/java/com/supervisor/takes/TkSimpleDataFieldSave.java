package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkSimpleDataFieldSave extends TkBaseWrap {

	public TkSimpleDataFieldSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String name = form.single("name");		
					final String defaultValue = form.single("default_value", StringUtils.EMPTY);
					final int order = Integer.parseInt(form.single("order"));
					final boolean isMandatory = Boolean.parseBoolean(form.single("is_mandatory"));
					final boolean takeLastValue = Boolean.parseBoolean(form.single("take_last_value"));
					final boolean mustEditOnce = Boolean.parseBoolean(form.single("must_edit_once"));
					final Long modelId = Long.parseLong(form.single("model_id"));
					
					final Long tableId = Long.parseLong(form.single("table_id", "0"));
					final Long tableModelId = Long.parseLong(form.single("table_model_id", "0"));
					
					final DataFieldType type = DataFieldType.valueOf(form.single("type_id"));
					final String description = form.single("description");
								
					DataSheetModel model = module.dataSheetModels().get(modelId);
					SimpleDataField itemSaved;
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					if(id > 0) {
						itemSaved = model.fields().simples().get(id); 
						itemSaved.update(itemSaved.code(), name, type, description); 			
					} else {	
						final CodeGenerator generator;
						if(tableId > 0) {							
							final DataSheetModel tableModel = module.dataSheetModels().get(tableModelId);
							TableDataField table = (TableDataField)tableModel.fields().get(tableId);
							
							generator = new BasicCodeGenerator(
											table.structure().fields(), 
											name
										);

							itemSaved = table.structure().fields().simples().add(generator.generate(), name, type, isMandatory, defaultValue, description);
						} else {
							generator = new BasicCodeGenerator(
											model.fields(),
											name
										);
							
							itemSaved = model.fields().simples().add(generator.generate(), name, type, isMandatory, defaultValue, description);
						}			
					}
					
					itemSaved.order(order);
					itemSaved.makeMandatory(isMandatory);
					itemSaved.makeMustEditOnce(mustEditOnce);
					itemSaved.takeLastValue(takeLastValue); 		
					itemSaved.defaultValue(defaultValue);
					
					final String msg;
					
					if(id > 0)
						msg = String.format("Le champ %s a été modifié avec succès !", itemSaved.name());
					else
						msg = String.format("Le champ %s a été créé avec succès !", itemSaved.name());
					
					if(tableId > 0) {
						return new RsForward(
								new RsFlash(
					                msg,
					                Level.FINE
					            ),
					            String.format("/collect/table/edit?id=%s&model=%s", tableId, tableModelId)
						    );
					}else {
						return new RsForward(
								new RsFlash(
					                msg,
					                Level.FINE
					            ),
					            "/collect/model/edit?id=" + model.id()
						    );
					}
				}
		);
	}	
}
