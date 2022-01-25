package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Profiles;
import com.minlessika.membership.domain.impl.PxAllApplications;
import com.minlessika.membership.domain.impl.PxProfiles;
import com.minlessika.membership.xe.XeApplication;
import com.minlessika.membership.xe.XeModule;
import com.minlessika.membership.xe.XeProfile;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
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
