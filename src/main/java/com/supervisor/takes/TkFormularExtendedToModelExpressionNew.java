package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExtendedToModelExpressionNew extends TkBaseWrap {

	public TkFormularExtendedToModelExpressionNew(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqHref.Smart href = new RqHref.Smart(req);		
					
					final Long modelId = Long.parseLong(href.single("model"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(href.single("formular"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final FormularExtendedToModelExpression itemSaved = formular.expressions().addModelExtension();
					
					return new RsForward(
						new RsFlash(
			                String.format("L'expression %s a été créée avec succès !", itemSaved.name()),
			                Level.FINE
			            ),
						String.format("/collect/aggregated-model/formular/extended-to-model-expression/edit?id=%s&model=%s&formular=%s", itemSaved.id(), model.id(), formular.id())
				    );
				}
		);
	}	
}
