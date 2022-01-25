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
import com.minlessika.supervisor.domain.FormularCaseExpression;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeFormularCaseExpression;
import com.minlessika.supervisor.xe.XeFormularFunc;
import com.minlessika.supervisor.xe.XeSupervisor;
import com.minlessika.supervisor.xe.XeWhenCase;

public final class TkFormularCaseExpressionEdit extends TkForm {

	public TkFormularCaseExpressionEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/formular_case_expression_edit.xsl";
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model")); 
		AggregatedModel model = module.aggregatedModels()
									 .get(modelId);
		
		Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		FormularDataField formular = model.formulars().get(formularId);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));
		content.add(new XeFormularFunc());	
		content.add(new XeAppend("formular_id", formular.id().toString()));
		content.add(new XeAppend("formular", formular.name()));
		content.add(new XeAppend("model_id", model.id().toString()));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels().get(modelId); 
		
		Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		FormularDataField formular = model.formulars().get(formularId);
		
		FormularCaseExpression item = (FormularCaseExpression)formular.expressions().get(id);

		return new XeChain(
				new XeFormularCaseExpression("item", item),
				new XeWhenCase(item.cases()) 
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(id == 0)
			return newItemToShow(req);
		else
			return preItemDataToShow(id, req);
	}	
}
