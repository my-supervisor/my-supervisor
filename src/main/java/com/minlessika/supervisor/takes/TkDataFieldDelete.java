package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkDataFieldDelete extends TkBaseWrap {

	public TkDataFieldDelete(Base base) {
		super(
				base, 
				req -> {
					Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
					Long tableId = Long.parseLong(new RqHref.Smart(req).single("table", "0"));
					Long tableModelId = Long.parseLong(new RqHref.Smart(req).single("table_model", "0"));
					
					final Supervisor module = new PxSupervisor(base, req);
					DataSheetModel model = module.dataSheetModels().get(modelId);
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));

					EditableDataField item = (EditableDataField)model.fields().get(id);
					
					if(item.userScope() == UserScope.SYSTEM)
						throw new IllegalArgumentException("Vous ne pouvez pas supprimer un champ système !");
										
					String name = item.name();
					model.fields().remove(item);
								
					if(tableId > 0) {
						return new RsForward(
							new RsFlash(
				                String.format("Le champ %s a été supprimé avec succès !", name),
				                Level.FINE
				            ),
				            String.format("/collect/model/edit?id=%s&model=%s", tableId, tableModelId)
					    );
					} else {
						return new RsForward(
							new RsFlash(
				                String.format("Le champ %s a été supprimé avec succès !", name),
				                Level.FINE
				            ),
				            "/collect/model/edit?id=" + modelId
					    );
					}
					
				}
		);
	}
}
