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
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeAggregatedModel;
import com.supervisor.xe.XeComparator;
import com.supervisor.xe.XeDataFieldType;
import com.supervisor.xe.XeRuleFormular;
import com.supervisor.xe.XeRuleParam;
import com.supervisor.xe.XeSupervisor;

public final class TkFormularEdit extends TkForm {

	public TkFormularEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/formular_edit.xsl";
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels()
									 .get(modelId);
		
		return new XeChain(
			new XeAggregatedModel(model)
		);
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
				
		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));
		content.add(new XeComparator());
		content.add(new XeDataFieldType());
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels().get(modelId); 
		FormularDataField item = model.formulars().get(id.get());

		return new XeChain(
				new XeAggregatedModel(model),
				new XeRuleFormular("item", item),
				new XeRuleParam(model.params())
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
