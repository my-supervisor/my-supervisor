package com.supervisor.takes;

import com.supervisor.domain.Profile;
import com.supervisor.domain.impl.PxAllProfiles;
import com.supervisor.sdk.utils.OptUUID;
import com.supervisor.xe.XeAccess;
import com.supervisor.xe.XeProfile;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class TkProfileAccessNotAdded extends TkForm {

	public TkProfileAccessNotAdded(Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/profile_access_add.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(Request req) throws IOException {

		UUID profileId = UUID.fromString(new RqHref.Smart(req).single("profile"));
		final Profile item = new PxAllProfiles(base).get(profileId);
		
		return new XeChain(
				new XeProfile(item), 
				new XeAccess(item.exceptedAccesses())
		); 
	}
	
	@Override
	protected XeSource preItemDataToShow(OptUUID id, Request req) throws IOException {
		return XeSource.EMPTY;
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return preItemDataToShow(id, req);
	}
}
