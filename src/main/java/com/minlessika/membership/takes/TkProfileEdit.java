package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.Profiles;
import com.minlessika.membership.domain.impl.PxAllProfiles;
import com.minlessika.membership.domain.impl.PxProfiles;
import com.minlessika.membership.xe.XeModule;
import com.minlessika.membership.xe.XeProfile;
import com.minlessika.membership.xe.XeProfileAccess;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkProfileEdit extends TkForm {

	public TkProfileEdit(Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/profile_edit.xsl";
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
		return XeSource.EMPTY; 
	}

	@Override
	protected XeSource preItemDataToShow(Long id, Request req) throws IOException {
		
		final Profile item = new PxAllProfiles(base).get(id);
		final Profiles profiles = new PxProfiles(base, item.module());
		return new XeChain(
				new XeProfile("item", item), 
				new XeProfile(profiles),
				new XeProfileAccess(item.accesses())
		); 
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return preItemDataToShow(id, req);
	}
}
