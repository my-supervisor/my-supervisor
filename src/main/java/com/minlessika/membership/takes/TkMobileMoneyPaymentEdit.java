package com.minlessika.membership.takes;

import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.billing.PaymentReceipts;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.billing.PaymentType;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XeMembership;
import com.minlessika.membership.xe.XeMobilePaymentReceipt;
import com.minlessika.membership.xe.XePaymentMethod;
import com.minlessika.membership.xe.XePaymentRequest;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkMobileMoneyPaymentEdit extends TkForm {

	public TkMobileMoneyPaymentEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/membership/xsl/mobile_money_payment_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Membership module = new DmMembership(base, req);
		final User user = module.user();
		
		Long requestId = Long.parseLong(new RqHref.Smart(req).single("request"));
		PaymentRequest request = user.paymentRequests().get(requestId);
		XeSource xePaymentRequest = new XePaymentRequest("request", request);
		
		Long methodId = Long.parseLong(new RqHref.Smart(req).single("method"));
		PaymentMethod method = module.paymentMethods()
			                         .where(PaymentMethod::type, PaymentType.MOBILE_MONEY)
			                         .get(methodId);
		
		XeSource xeMethod = new XePaymentMethod("method", method);
		
		PaymentReceipts receipts = request.order()
				                          .receipts()
							              .where(PaymentReceipt::method, method.id())
							              .where(PaymentReceipt::state, PaymentReceiptState.PENDING);
		
		XeSource xeReceiptPending = XeSource.EMPTY;
		if(receipts.any())
			xeReceiptPending = new XeMobilePaymentReceipt(
									receipts.first()
							   );
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeMembership(module));
		content.add(xePaymentRequest);
		content.add(xeMethod);
		content.add(xeReceiptPending);
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		return XeSource.EMPTY;
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeDirectives(dir);
	}	
}
