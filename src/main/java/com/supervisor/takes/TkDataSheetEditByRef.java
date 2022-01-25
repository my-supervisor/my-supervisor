package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkDataSheetEditByRef extends TkBaseWrap {

	public TkDataSheetEditByRef(Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					final DataSheets items = module.dataSheets();
					final String reference = new RqHref.Smart(req).single("ref");

					if(items.where(DataSheet::reference, reference).isEmpty()) {
						return new RsForward(
								new RsFlash(
										"Vous n'êtes pas autorisé à accéder à cette feuille de données !",
					                Level.FINE
					            ),
					            "/collect/model"
						    );
					}
					
					final DataSheet item = items.where(DataSheet::reference, reference).first();
						
					return new RsForward(
			            String.format("/collect/sheet/edit?id=%s", item.id())
				    );
				}
		);
	}
}
