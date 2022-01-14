package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkListDataFieldSourceSave extends TkBaseWrap {

	public TkListDataFieldSourceSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Long modelId = Long.parseLong(form.single("model_id"));
					final DataSheetModel model = module.dataSheetModels().get(modelId);
					
					final Long fieldId = Long.parseLong(form.single("field_id"));
					final ListDataField field = (ListDataField)model.fields().get(fieldId); 
					
					final Long listModelId = Long.parseLong(form.single("list_model_id"));
					final DataModel listModel = module.dataModels().get(listModelId);
					
					final Long fieldToDisplayId = Long.parseLong(form.single("field_to_display_id"));
					final DataField fieldToDisplay = listModel.fields().get(fieldToDisplayId);
					
					final Long orderFieldId = Long.parseLong(form.single("order_field_id"));
					final DataField orderField = listModel.fields().get(orderFieldId);
					
					final Long tableId = Long.parseLong(form.single("table_id", "0"));
					final Long tableModelId = Long.parseLong(form.single("table_model_id", "0"));
													
					final ListDataFieldSource itemSaved;
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					if(id > 0) {
						itemSaved = field.sources().get(id); 
						itemSaved.update(listModel, fieldToDisplay, orderField);	 	
					} else {			
						itemSaved = field.sources().add(listModel, fieldToDisplay, orderField);
					}
					
					final String msg;
					
					if(id > 0)
						msg = "La source de données a été modifiée avec succès !";
					else
						msg = "La source de données a été créée avec succès !";
					
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
