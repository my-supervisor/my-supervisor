package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.apache.commons.collections.IteratorUtils;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.Resource;
import com.supervisor.domain.ResourceType;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeResource;
import com.supervisor.xe.XeSubscriber;
import com.supervisor.xe.XeSupervisor;

public final class TkSharingEdit extends TkForm {

	public TkSharingEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/sharing_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		Smart params = new RqHref.Smart(req);
		
		ResourceType type = ResourceType.valueOf(params.single("type"));
		Long resourceId = Long.parseLong(params.single("resource"));
		Resource resource = module.resources().resource(type, resourceId);
		
		if(new RqUser(base, req).notOwn(resource)) {
			throw new IllegalArgumentException("Vous n'êtes pas propriétaire de cette ressource !");
		}
		
		XeSource xeResource = new XeResource(resource);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeSupervisor(module));
		content.add(xeResource);
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		return XeSource.EMPTY;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeSubscriber((List<String>)IteratorUtils.toList(form.param("item_email").iterator()));
	}	
}
