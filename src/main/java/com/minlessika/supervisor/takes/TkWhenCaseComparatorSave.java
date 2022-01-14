package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularCaseExpression;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.WhenCase;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkWhenCaseComparatorSave extends TkBaseWrap {

	public TkWhenCaseComparatorSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
					
					final Comparator comparator = Comparator.valueOf(form.single("comparator_id"));
					
					final Long modelId = Long.parseLong(form.single("model_id"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final Long formularId = Long.parseLong(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					Long expressionId = Long.parseLong(form.single("expression_id"));
					FormularCaseExpression expression = (FormularCaseExpression)formular.expressions().get(expressionId);
					
					final Long id = Long.parseLong(form.single("id"));
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
