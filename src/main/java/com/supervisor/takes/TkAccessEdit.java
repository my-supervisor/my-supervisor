package com.supervisor.takes;

import com.supervisor.domain.Access;
import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.domain.impl.PxAccesses;
import com.supervisor.xe.XeAccess;
import com.supervisor.xe.XeAccessParam;
import com.supervisor.xe.XeMembership;
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

public final class TkAccessEdit extends TkForm {

	public TkAccessEdit(Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/access_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		final Membership module = new DmMembership(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeMembership(module)); 
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(Long id, Request req) throws IOException {
		
		final Access item = new PxAccesses(base).get(id);
		return new XeChain(
				new XeAccess("item", item), 
				new XeAccessParam(item.parameters())
		); 
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return preItemDataToShow(id, req);
	}
}
