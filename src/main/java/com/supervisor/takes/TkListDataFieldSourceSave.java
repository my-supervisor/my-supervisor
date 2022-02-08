package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkListDataFieldSourceSave extends TkBaseWrap {

	public TkListDataFieldSourceSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final UUID modelId = UUID.fromString(form.single("model_id"));
					final DataSheetModel model = module.dataSheetModels().get(modelId);
					
					final UUID fieldId = UUID.fromString(form.single("field_id"));
					final ListDataField field = (ListDataField)model.fields().get(fieldId); 
					
					final UUID listModelId = UUID.fromString(form.single("list_model_id"));
					final DataModel listModel = module.dataModels().get(listModelId);
					
					final UUID fieldToDisplayId = UUID.fromString(form.single("field_to_display_id"));
					final DataField fieldToDisplay = listModel.fields().get(fieldToDisplayId);
					
					final UUID orderFieldId = UUID.fromString(form.single("order_field_id"));
					final DataField orderField = listModel.fields().get(orderFieldId);
					
					final OptUUID tableId = new OptUUID(form.single("table_id", "0"));
					final OptUUID tableModelId = new OptUUID(form.single("table_model_id", "0"));
													
					final ListDataFieldSource itemSaved;
					
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(id.isPresent()) {
						itemSaved = field.sources().get(id.value());
						itemSaved.update(listModel, fieldToDisplay, orderField);	 	
					} else {			
						itemSaved = field.sources().add(listModel, fieldToDisplay, orderField);
					}
					
					final String msg;
					
					if(id.isPresent())
						msg = "La source de données a été modifiée avec succès !";
					else
						msg = "La source de données a été créée avec succès !";
					
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
