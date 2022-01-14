package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.Response;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.sdk.utils.BasicCodeGenerator;
import com.minlessika.sdk.utils.CodeGenerator;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkDataFieldTableSave extends TkBaseWrap {

	public TkDataFieldTableSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String name = form.single("name");		
					final int order = Integer.parseInt(form.single("order"));
					final Long modelId = Long.parseLong(form.single("model_id"));
					final String description = form.single("description");
						
					DataSheetModel model = module.dataSheetModels().get(modelId);
					TableDataField itemSaved;
					Response response;
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					if(id > 0) {
						itemSaved = model.fields().tables().get(id); 
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
