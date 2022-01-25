package com.supervisor.takes;

import com.supervisor.domain.Application;
import com.supervisor.domain.Profiles;
import com.supervisor.domain.impl.PxAllApplications;
import com.supervisor.domain.impl.PxProfiles;
import com.supervisor.xe.XeApplication;
import com.supervisor.xe.XeModule;
import com.supervisor.xe.XeProfile;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkApplicationEdit extends TkForm {

	public TkApplicationEdit(Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/application_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeModule());
		content.add(itemToShow);
		
		return content;
	}
	
	@Override
	protected XeSource newItemToShow(Request req) throws IOException {
		
		final Long userId = Long.parseLong(new RqHref.Smart(req).single("user"));
		return new XeChain(
					new XeAppend("user_id", userId.toString())
			   ); 
	}

	@Override
	protected XeSource preItemDataToShow(Long id, Request req) throws IOException {
		
		final Application item = new PxAllApplications(base).get(id);
		final Profiles profiles = new PxProfiles(base, item.module());
		return new XeChain(
				new XeApplication("item", item), 
				new XeProfile(profiles)
		); 
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return preItemDataToShow(id, req);
	}
}
