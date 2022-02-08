package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Response;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkDataFieldTableSave extends TkBaseWrap {

	public TkDataFieldTableSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String name = form.single("name");		
					final int order = Integer.parseInt(form.single("order"));
					final UUID modelId = UUID.fromString(form.single("model_id"));
					final String description = form.single("description");
						
					DataSheetModel model = module.dataSheetModels().get(modelId);
					TableDataField itemSaved;
					Response response;
					
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(id.isPresent()) {
						itemSaved = model.fields().tables().get(id.value());
						itemSaved.update(itemSaved.code(), name, description);		
						itemSaved.makeMandatory(true);
						itemSaved.order(order);
						
						response = new RsForward(
								new RsFlash(
										String.format("Le champ %s a été modifié avec succès !", itemSaved.name()),
					                Level.FINE
					            ),
					            "/collect/model/edit?id=" + model.id()
						    );
					} else {
						final CodeGenerator generator = new BasicCodeGenerator(
															model.fields(), 
															name
														);
						
						itemSaved = model.fields().tables().add(generator.generate(), name, true, description);
						itemSaved.order(order);
						
						response = new RsForward(
								new RsFlash(
										String.format("Le modèle %s a été créé avec succès !", itemSaved.name()),
						                Level.FINE
						            ),
						            String.format("/collect/table/edit?id=%s&model=%s", itemSaved.id(), model.id())
							    );
					}
					
					return response;	
				}
		);
	}	
}
