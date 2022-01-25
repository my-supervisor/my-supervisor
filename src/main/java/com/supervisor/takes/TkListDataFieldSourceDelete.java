package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkListDataFieldSourceDelete extends TkBaseWrap {

	public TkListDataFieldSourceDelete(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
					final Long fieldId = Long.parseLong(new RqHref.Smart(req).single("field"));
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					final Long tableId = Long.parseLong(new RqHref.Smart(req).single("table", "0"));
					
					final DataSheetModel model = module.dataSheetModels().get(modelId);
					final ListDataField field = (ListDataField)model.fields().get(fieldId); 
					final ListDataFieldSource item = field.sources().get(id);
					
					field.sources().remove(item);
					
					final String msg = "La source de données a été supprimée avec succès !";

					if(tableId > 0) {
						return new RsForward(
								new RsFlash(
					                msg,
					                Level.FINE
					            ),
					            String.format("/collect/field/list/edit?id=%s&model=%s&table=%s", field.id(), model.id(), tableId)
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
