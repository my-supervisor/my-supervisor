package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkDataFieldSave extends TkBaseWrap {

	public TkDataFieldSave(final Base base) {
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
					
					final String tableParam = form.single("table_id", "0");
					final Long tableId;
					if(StringUtils.isBlank(tableParam))
						tableId = 0L;
					else			
						tableId = Long.parseLong(form.single("table_id", "0"));
					
					final DataFieldType type = DataFieldType.valueOf(form.single("type_id"));
					final DataFieldStyle style = DataFieldStyle.valueOf(form.single("style_id"));
					final String description = form.single("description");
								
					DataSheetModel model = module.dataSheetModels().get(modelId);
					SimpleDataField itemSaved;
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					if(id > 0) {
						itemSaved = model.fields().simples().get(id); 
						itemSaved.update(itemSaved.code(), name, type, style, description);			
					} else {			
						final String code = form.single("code");
						if(tableId > 0) {
							TableDataField table = (TableDataField)model.fields().get(tableId);
							
							itemSaved = table.structure().fields().simples().add(code, name, type, isMandatory, defaultValue, description);
						} else {
							itemSaved = model.fields().simples().add(code, name, type, isMandatory, defaultValue, description);
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
					            String.format("/collect/table/edit?id=%s&model=%s", tableId, model.id())
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
