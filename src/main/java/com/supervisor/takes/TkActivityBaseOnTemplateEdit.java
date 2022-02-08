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
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplates;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivityParam;
import com.supervisor.xe.XeActivityTemplate;
import com.supervisor.xe.XePeriodicity;
import com.supervisor.xe.XeSupervisor;

public final class TkActivityBaseOnTemplateEdit extends TkForm {

	public TkActivityBaseOnTemplateEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/activity_base_on_template_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XePeriodicity());
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		Supervisor module = new PxSupervisor(base, req);		
		ActivityTemplates templates = module.activityTemplates();
		UUID templateId = UUID.fromString(new RqHref.Smart(req).single("template"));
		
		ActivityTemplate template = templates.get(templateId);
		XeSource xeItem = new XeActivityTemplate("item", template); 
		
		return new XeChain(
				xeItem,
				new XeActivityParam(template.params())
		);
	}
	
	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		return XeSource.EMPTY;
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeDirectives(dir);
	}	
}
