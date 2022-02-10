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

import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplateRelease;
import com.supervisor.domain.ActivityTemplates;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivityTemplate;
import com.supervisor.xe.XeActivityTemplateRelease;
import com.supervisor.xe.XePeriodicity;
import com.supervisor.xe.XeSupervisor;

public final class TkActivityTemplateReleaseEdit extends TkForm {

	public TkActivityTemplateReleaseEdit(Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/activity_template_release_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
				
		final RqHref.Smart href = new RqHref.Smart(req);
		
		final Supervisor module = new PxSupervisor(base, req);
		ActivityTemplates templates = module.activityTemplates();
		UUID templateId = UUID.fromString(href.single("template"));
		ActivityTemplate template = templates.get(templateId);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XePeriodicity());
		content.add(new XeAppend("menu", "activities"));
		content.add(new XeSupervisor(module));
		content.add(new XeActivityTemplate("template", template));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(OptUUID id, Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		ActivityTemplates templates = module.activityTemplates();
		
		UUID templateId = UUID.fromString(new RqHref.Smart(req).single("template"));
		ActivityTemplate template = templates.get(templateId);
		
		ActivityTemplateRelease release = template.releases().get(id.get());
		
		return new XeChain(
				new XeActivityTemplateRelease("item", release)
		);
	}
	
	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(!id.isPresent()) {
			final RqHref.Smart href = new RqHref.Smart(req);
			
			return new XeChain(
					new XeAppend("release_activity_src_id", href.single("activity")),
					new XeActivityTemplateRelease(dir)
			);
		}else {
			return new XeActivityTemplateRelease(dir); 
		}
	}
	
	@Override
	protected XeSource newItemToShow(Request req) throws IOException {
		
		final RqHref.Smart href = new RqHref.Smart(req);
		
		return new XeChain(
				new XeAppend("release_activity_src_id", href.single("activity"))
		);
	}
}
