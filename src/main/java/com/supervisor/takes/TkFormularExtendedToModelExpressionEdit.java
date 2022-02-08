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
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeFormularExtendedToModelExpression;
import com.supervisor.xe.XeFormularExtendedToModelSource;
import com.supervisor.xe.XeFormularFunc;
import com.supervisor.xe.XeSupervisor;

public final class TkFormularExtendedToModelExpressionEdit extends TkForm {

	public TkFormularExtendedToModelExpressionEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/formular_extended_to_model_expression_edit.xsl";
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		final UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels()
									 .get(modelId);
		
		final UUID formularId = UUID.fromString(new RqHref.Smart(req).single("formular"));
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
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels().get(modelId); 
		
		UUID formularId = UUID.fromString(new RqHref.Smart(req).single("formular"));
		FormularDataField formular = model.formulars().get(formularId);
		
		final FormularExtendedToModelExpression item = (FormularExtendedToModelExpression)formular.expressions().get(id.value());

		return new XeChain(
			new XeFormularExtendedToModelExpression("item", item),
			new XeFormularExtendedToModelSource(item.sources()) 
		);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(id.isEmpty())
			return newItemToShow(req);
		else
			return preItemDataToShow(id, req);
	}	
}
