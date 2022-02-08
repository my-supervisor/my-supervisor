package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkListDataFieldSourceActivate extends TkBaseWrap {

	public TkListDataFieldSourceActivate(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
					final UUID fieldId = UUID.fromString(new RqHref.Smart(req).single("field"));
					final UUID id = UUID.fromString(new RqHref.Smart(req).single("id"));
					final OptUUID tableId = new OptUUID(new RqHref.Smart(req).single("table", "0"));
					final OptUUID tableModelId = new OptUUID(new RqHref.Smart(req).single("table_model", "0"));
					final boolean active = Boolean.parseBoolean(new RqHref.Smart(req).single("active"));
					
					final DataSheetModel model = module.dataSheetModels().get(modelId);
					final ListDataField field = (ListDataField)model.fields().get(fieldId); 
					final ListDataFieldSource item = field.sources().get(id);
					
					item.activate(active);
					
					final String msg;
					
					if(active)
						msg = "La source de données a été activée avec succès !";
					else
						msg = "La source de données a été désactivée avec succès !";
					
					if(tableId.isPresent()) {
						return new RsForward(
								new RsFlash(
					                msg,
					                Level.FINE
					            ),
					            String.format("/collect/field/list/edit?id=%s&model=%s&table=%s&table_model=%s", field.id(), model.id(), tableId, tableModelId)
						    );
					}else {
						return new RsForward(
								new RsFlash(
					                msg,
					                Level.FINE
					            ),
					            String.format("/collect/field/list/edit?id=%s&model=%s", field.id(), model.id())
						    );
					}
				}
		);
	}	
}
