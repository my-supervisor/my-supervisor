package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.WhenCase;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeComparator;
import com.supervisor.xe.XeSupervisor;
import com.supervisor.xe.XeWhenCase;

public final class TkWhenCaseComparatorEdit extends TkForm {

	public TkWhenCaseComparatorEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/when_case_comparison_edit.xsl";
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels()
									 .get(modelId);
		
		UUID formularId = UUID.fromString(new RqHref.Smart(req).single("formular"));
		FormularDataField formular = model.formulars().get(formularId);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));
		content.add(new XeComparator());	
		content.add(new XeAppend("formular_id", formular.id().toString()));
		content.add(new XeAppend("model_id", model.id().toString()));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels().get(modelId); 
		
		UUID formularId = UUID.fromString(new RqHref.Smart(req).single("formular"));
		FormularDataField formular = model.formulars().get(formularId);
		
		UUID expressionId = UUID.fromString(new RqHref.Smart(req).single("expression"));
		FormularCaseExpression expression = (FormularCaseExpression)formular.expressions().get(expressionId);

		WhenCase item = expression.cases().get(id.get());
		
		return new XeChain(
				new XeWhenCase("item", item)
		);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(!id.isPresent())
			return newItemToShow(req);
		else
			return preItemDataToShow(id, req);
	}	
}
