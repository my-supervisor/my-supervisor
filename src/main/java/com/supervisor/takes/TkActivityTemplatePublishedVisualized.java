package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivityTemplatePublished;
import com.supervisor.xe.XeSupervisor;

public final class TkActivityTemplatePublishedVisualized extends TkForm {

	public TkActivityTemplatePublishedVisualized(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/activity_template_published_visualized.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "kpi_store"));
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		final ActivityTemplatePublished item = module.activityTemplatesPublished().get(id);
		
		/* Enregistrer la vue */
		item.view();
		
		return new XeActivityTemplatePublished("item", item);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return XeSource.EMPTY; 
	}	
	
	@Override
	protected XeSource newItemToShow(Request req) throws IOException {
		return XeSource.EMPTY;
	}
}
