package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.DataSheets;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkDataSheetDelete extends TkBaseWrap {

	public TkDataSheetDelete(Base base) {
		super(
				base, 
				req ->{
					final Supervisor module = new PxSupervisor(base, req);
					DataSheets myDataSheets = module.dataSheets();
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));

					DataSheet item = myDataSheets.get(id);
					if(new RqUser(base, req).notOwn(item)) {
						throw new IllegalArgumentException("Vous ne pouvez pas supprimer la feuille d'un modèle partagé !");
					}
					
					String reference = item.reference();
					DataSheetModel model = item.model();
					myDataSheets.remove(item);
								
					module.activityNotification().publish(model);
					
					return new RsForward(
						new RsFlash(
			                String.format("Le feuille %s a été supprimée avec succès !", reference),
			                Level.FINE
			            ),
			            "/collect/sheet"
				    );
				}
		);
	}
}
