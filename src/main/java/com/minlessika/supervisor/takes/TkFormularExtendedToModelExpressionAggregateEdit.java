package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeAggregateFunc;
import com.minlessika.supervisor.xe.XeFormularExtendedToModelExpression;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkFormularExtendedToModelExpressionAggregateEdit extends TkForm {

	public TkFormularExtendedToModelExpressionAggregateEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/supervisor/xsl/formular_extended_to_model_expression_aggregate_edit.xsl";
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		throw new UnsupportedOperationException("TkFormularExtendedToModelExpressionAggregateEdit#newItemToShow"); 
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		final Long modelId = Long.parseLong(new RqHref.Smart(req).single("model")); 
		final AggregatedModel amodel = module.aggregatedModels()
									  		 .get(modelId);
		
		final Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		final FormularDataField formular = amodel.formulars().get(formularId);
		
		final List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));
		content.add(new XeAggregateFunc());	
		content.add(new XeAppend("formular_id", formular.id().toString()));
		content.add(new XeAppend("formular", formular.name()));
		content.add(new XeAppend("model_id", amodel.id().toString()));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		final Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
		final AggregatedModel model = module.aggregatedModels().get(modelId); 
		
		final Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		final FormularDataField formular = model.formulars().get(formularId);
		
		final FormularExtendedToModelExpression item = (FormularExtendedToModelExpression)formular.expressions().get(id);

		return new XeChain(
			new XeFormularExtendedToModelExpression("item", item)
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {		
		return preItemDataToShow(id, req);
	}	
}
