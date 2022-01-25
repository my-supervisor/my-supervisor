package com.minlessika.membership.takes;

import com.minlessika.membership.billing.Tax;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XeTax;
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

public final class TkTaxEdit extends TkForm {


	public TkTaxEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/tax_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(itemToShow);
		
		return content;
	}
	
	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		final Membership module = new DmMembership(base, req);
		final Tax tax = module.taxes().get(id);
		
		XeSource xeTax = new XeTax("item", tax);		
		return new XeChain(
				xeTax
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeTax(dir);
	}
}
