package com.supervisor.takes;

import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.billing.PaymentReceipts;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.billing.PaymentType;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.xe.XeMembership;
import com.supervisor.xe.XeMobilePaymentReceipt;
import com.supervisor.xe.XePaymentMethod;
import com.supervisor.xe.XePaymentRequest;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
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
		return "/xsl/mobile_money_payment_edit.xsl";
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
