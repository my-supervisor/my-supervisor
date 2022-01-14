package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkImportDataSheetSave extends TkBaseWrap {

	public TkImportDataSheetSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Long id = Long.parseLong(form.single("id"));
					final Long sourceId = Long.parseLong(form.single("source_id"));
					
					final DataSheetModel source = module.dataSheetModels().get(sourceId);
					final DataSheetModel target = module.dataSheetModels().get(id);
					
					target.importDataFrom(source);
					
					return new RsForward(
							new RsFlash(
									String.format("L'importation de données dans le modèle %s a été effectuée avec succès !", target.name()),
					                Level.FINE
				            ),
				            "/collect/model/edit?id=" + target.id()
					);
				}
		);
	}	
}
