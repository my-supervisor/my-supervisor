package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.DataSheetModels;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkDataSheetModelDelete extends TkBaseWrap {

	public TkDataSheetModelDelete(Base base) {
		super(
				base, 
				(req)->{
					final Supervisor module = new PxSupervisor(base, req);
					DataSheetModels myDataFields = module.dataSheetModels();
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));

					DataSheetModel item = myDataFields.get(id);
					if(new RqUser(base, req).notOwn(item)) {
						throw new IllegalArgumentException("Vous ne pouvez pas supprimer un modèle partagé !");
					}
					
					String name = item.name();
					myDataFields.remove(item);
								
					return new RsForward(
						new RsFlash(
			                String.format("Le modèle %s a été supprimé avec succès !", name),
			                Level.FINE
			            ),
			            "/collect/model"
				    );
				}
		);
	}
}
