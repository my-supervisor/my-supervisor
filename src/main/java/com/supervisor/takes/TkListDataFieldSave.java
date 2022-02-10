package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkListDataFieldSave extends TkBaseWrap {

	public TkListDataFieldSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String name = form.single("name");
					final int order = Integer.parseInt(form.single("order"));
					final boolean isMandatory = Boolean.parseBoolean(form.single("is_mandatory"));
					final boolean canBeUpdated = Boolean.parseBoolean(form.single("can_be_updated"));
					final boolean mustEditOnce = Boolean.parseBoolean(form.single("must_edit_once"));
					final int limit = Integer.parseInt(form.single("limit"));
					
					final UUID modelId = UUID.fromString(form.single("model_id"));
					final DataSheetModel model = module.dataSheetModels().get(modelId);
					
					final OrderDirection orderDirection = OrderDirection.valueOf(form.single("order_direction_id"));
					
					final OptUUID tableId = new OptUUID(form.single("table_id", "0"));
					final OptUUID tableModelId = new OptUUID(form.single("table_model_id", "0"));
					
					final DataFieldType type = DataFieldType.valueOf(form.single("type_id"));
					final String description = form.single("description");
													
					final ListDataField itemSaved;
					
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(id.isPresent()) {
						itemSaved = model.fields().lists().get(id.get());
						itemSaved.update(itemSaved.code(), name, type, description);			
					} else {						
						final CodeGenerator generator;
						if(tableId.isPresent()) {
							final DataSheetModel tableModel = module.dataSheetModels().get(tableModelId.get());
							TableDataField table = (TableDataField)tableModel.fields().get(tableId.get());
							
							generator = new BasicCodeGenerator(
											table.structure().fields(), 
											name
										);
							
							itemSaved = table.structure().fields().lists().add(generator.generate(), name, type, isMandatory, description);
						} else {
							generator = new BasicCodeGenerator(
											model.fields(),
											name
										);
							
							itemSaved = model.fields().lists().add(generator.generate(), name, type, isMandatory, description);
						}			
					}
					
					itemSaved.order(order);
					itemSaved.makeMandatory(isMandatory);
					itemSaved.makeMustEditOnce(mustEditOnce);
					itemSaved.makeCanBeUpdated(canBeUpdated);
					
					itemSaved.update(orderDirection, limit);
					
					final String msg;
					
					if(id.isPresent())
						msg = String.format("Le champ %s a été modifié avec succès !", itemSaved.name());
					else
						msg = String.format("Le champ %s a été créé avec succès !", itemSaved.name());
					
					if(tableId.isPresent()) {
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
