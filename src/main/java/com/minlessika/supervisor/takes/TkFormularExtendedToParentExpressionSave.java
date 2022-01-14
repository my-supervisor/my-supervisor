package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToParentExpressionSave extends TkBaseWrap {

	public TkFormularExtendedToParentExpressionSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final Long modelId = Long.parseLong(form.single("model_id"));
					final AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final Long referenceId = Long.parseLong(form.single("reference_id"));
					final ListDataField reference = model.fields().lists().get(referenceId); 
					
					final FormularExtendedToParentExpression itemSaved;
					
					final Long id = Long.parseLong(form.single("id", "0"));
					if(id > 0) {	
						throw new IllegalArgumentException("You can't modified a reference of extended parent formular expression !");
					} else {
						itemSaved = formular.expressions().addParentExtension(reference);
					}

					return new RsForward(
						new RsFlash(
			                "L'expression a été enregistrée avec succès !",
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/extended-to-parent-expression/edit?id=%s&model=%s&formular=%s", itemSaved.id(), model.id(), formular.id())
				    );
				}
		);
	}	
}
