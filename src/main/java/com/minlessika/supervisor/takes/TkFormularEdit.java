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
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeAggregatedModel;
import com.minlessika.supervisor.xe.XeComparator;
import com.minlessika.supervisor.xe.XeDataFieldType;
import com.minlessika.supervisor.xe.XeRuleFormular;
import com.minlessika.supervisor.xe.XeRuleParam;
import com.minlessika.supervisor.xe.XeSupervisor;

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
		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model")); 
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
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels().get(modelId); 
		FormularDataField item = model.formulars().get(id);

		return new XeChain(
				new XeAggregatedModel(model),
				new XeRuleFormular("item", item),
				new XeRuleParam(model.params())
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
