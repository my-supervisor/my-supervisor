package com.minlessika.membership.takes;

import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XeMembership;
import com.minlessika.membership.xe.XePaymentMethod;
import com.minlessika.membership.xe.XePaymentRequest;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkPaymentRequestEdit extends TkForm {

	public TkPaymentRequestEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/membership/xsl/payment_request_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
	
		final Membership module = new DmMembership(base, req);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeMembership(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Membership module = new DmMembership(base, req);
		final User user = module.user();
		
		PaymentRequest item = user.paymentRequests().get(id);
		return new XeChain(
				new XePaymentRequest("item", item),
				new XePaymentMethod(
						module.paymentMethods()
					          .where(PaymentMethod::online, true)
					          .where(PaymentMethod::enabled, true)
					          .items()
				)
			);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeDirectives(dir);
	}	
}
