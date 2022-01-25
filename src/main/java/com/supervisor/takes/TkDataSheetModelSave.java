package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import org.takes.Response;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.Activity;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkDataSheetModelSave extends TkBaseWrap {

	public TkDataSheetModelSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);		
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String name = form.single("name");
					final boolean canMergeAtSameDate = Boolean.parseBoolean(form.single("can_merge_at_same_date"));
					final String description = form.single("description");
					final Long activityId = Long.parseLong(form.single("activity_id"));
					
					final Activity activity = module.activities().get(activityId);
					
					DataSheetModel itemSaved;
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					Response response;
					
					if(id > 0) {
						itemSaved = activity.forms().get(id);
						
						if(new RqUser(base, req).notOwn(itemSaved)) {
							throw new IllegalArgumentException("Vous ne pouvez pas modifier un modèle partagé !");
						}
						
						itemSaved.update(itemSaved.code(), name, description);
						itemSaved.merging(canMergeAtSameDate);
						
						final boolean active = Boolean.parseBoolean(form.single("active", "false"));
						itemSaved.activate(active); 
						
						response = new RsForward(
								new RsFlash(
										String.format("Le modèle %s a été modifié avec succès !", itemSaved.name()),
						                Level.FINE
						            ),
						            "/collect/model"
							    );
					} else {
						final CodeGenerator generator = new BasicCodeGenerator(
															activity.dataModels(), 
															name
														);
						
						itemSaved = activity.forms().add(generator.generate(), name, description);
						itemSaved.merging(canMergeAtSameDate);
						
						response = new RsForward(
								new RsFlash(
										String.format("Le modèle %s a été créé avec succès !", itemSaved.name()),
						                Level.FINE
						            ),
						            "/collect/model/edit?id=" + itemSaved.id()
							    );
					}
								
					return response;
				}
		);
	}
}
