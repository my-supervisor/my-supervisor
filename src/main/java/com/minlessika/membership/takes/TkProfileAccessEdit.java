package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.ProfileAccess;
import com.minlessika.membership.domain.impl.PxAllProfiles;
import com.minlessika.membership.xe.XeProfileAccess;
import com.minlessika.membership.xe.XeProfileAccessParam;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkProfileAccessEdit extends TkForm {

	public TkProfileAccessEdit(Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/profile_access_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(Long id, Request req) throws IOException {

		final Long profileId = Long.parseLong(new RqHref.Smart(req).single("profile"));
		final Profile profile = new PxAllProfiles(base).get(profileId);
		final ProfileAccess item = profile.accesses().get(id);
		
		return new XeChain(
				new XeProfileAccess("item", item), 
				new XeProfileAccessParam(item.parameterValues())
		); 
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return preItemDataToShow(id, req);
	}
}
