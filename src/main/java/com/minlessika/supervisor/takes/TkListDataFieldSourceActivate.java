package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkListDataFieldSourceActivate extends TkBaseWrap {

	public TkListDataFieldSourceActivate(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
					final Long fieldId = Long.parseLong(new RqHref.Smart(req).single("field"));
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					final Long tableId = Long.parseLong(new RqHref.Smart(req).single("table", "0"));
					final Long tableModelId = Long.parseLong(new RqHref.Smart(req).single("table_model", "0"));
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
					
					if(tableId > 0) {
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
