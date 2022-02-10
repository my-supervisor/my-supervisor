package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.supervisor.domain.impl.PxProfiles;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.xe.XeProfile;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivityTemplatePublished;
import com.supervisor.xe.XeSupervisor;

public final class TkActivityTemplatePublishedEdit extends TkForm {

	public TkActivityTemplatePublishedEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/activity_template_published_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		UUID templateId = UUID.fromString(new RqHref.Smart(req).single("template"));
		ActivityTemplate template = module.activityTemplates().get(templateId);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "activities"));
		content.add(new XeAppend("template_name", template.name()));
		content.add(new XeAppend("template_id", templateId.toString()));
		content.add(new XeProfile(new PxProfiles(base)));
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		final ActivityTemplatePublished item = module.activityTemplatesPublished().get(id.get());
		
		return new XeActivityTemplatePublished("item", item);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeActivityTemplatePublished(dir); 
	}	
	
	@Override
	protected XeSource newItemToShow(Request req) throws IOException {
		return XeSource.EMPTY;
	}
}
