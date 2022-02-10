package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.supervisor.domain.UserScope;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkDataFieldDelete extends TkBaseWrap {

	public TkDataFieldDelete(Base base) {
		super(
				base, 
				req -> {
					UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
					OptUUID tableId = new OptUUID(new RqHref.Smart(req).single("table", "0"));
					OptUUID tableModelId = new OptUUID(new RqHref.Smart(req).single("table_model", "0"));
					
					final Supervisor module = new PxSupervisor(base, req);
					DataSheetModel model = module.dataSheetModels().get(modelId);
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));

					EditableDataField item = (EditableDataField)model.fields().get(id.get());
					
					if(item.userScope() == UserScope.SYSTEM)
						throw new IllegalArgumentException("Vous ne pouvez pas supprimer un champ système !");
										
					String name = item.name();
					model.fields().remove(item);
								
					if(tableId.isPresent()) {
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
