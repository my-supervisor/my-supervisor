package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.WhenCase;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkWhenCaseComparatorSave extends TkBaseWrap {

	public TkWhenCaseComparatorSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final Comparator comparator = Comparator.valueOf(form.single("comparator_id"));
					
					final UUID modelId = UUID.fromString(form.single("model_id"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID formularId = UUID.fromString(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					UUID expressionId = UUID.fromString(form.single("expression_id"));
					FormularCaseExpression expression = (FormularCaseExpression)formular.expressions().get(expressionId);
					
					final UUID id = UUID.fromString(form.single("id"));
					final WhenCase itemSaved = expression.cases().get(id);
					itemSaved.updateComparator(comparator);
					
					return new RsForward(
							new RsFlash(
				                "L'opérateur de comparaison de la possibilité a été modifié avec succès !",
				                Level.FINE
				            ),
							String.format("/collect/aggregated-model/formular/case-expression/edit?id=%s&formular=%s&model=%s", expression.id(), formular.id(), model.id())
					    );
				}
		);
	}	
}
