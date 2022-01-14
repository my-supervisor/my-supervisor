package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.billing.PaymentRequestStatus;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.impl.PxApplication;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;
import java.util.Map;

public final class PxPaymentRequest extends DomainRecordable<PaymentRequest> implements PaymentRequest {

	public PxPaymentRequest(Record<PaymentRequest> record) {
		super(record);
	}

	@Override
	public Order order() throws IOException {
		return new PxOrder(record.of(PaymentRequest::order));
	}

	@Override
	public String reference() throws IOException {
		return record.valueOf(PaymentRequest::reference);
	}

	@Override
	public String object() throws IOException {
		return record.valueOf(PaymentRequest::object);
	}

	@Override
	public double amount() throws IOException {
		return record.valueOf(PaymentRequest::amount);
	}

	@Override
	public String notes() throws IOException {
		return record.valueOf(PaymentRequest::notes);
	}

	@Override
	public PaymentRequestStatus status() throws IOException {
		return record.valueOf(PaymentRequest::status);
	}

	@Override
	public Map<String, String> metadata() throws IOException {
		return record.valueOf(PaymentRequest::metadata);
	}

	@Override
	public void accept() throws IOException {
		
		if(status() != PaymentRequestStatus.PENDING)
			throw new IllegalArgumentException("Payment request must be in pending status before accepting it !");
		
		record.entryOf(PaymentRequest::status, PaymentRequestStatus.ACCEPTED)
		      .update();
	}

	@Override
	public void cancel() throws IOException {
		
		if(status() != PaymentRequestStatus.PENDING)
			throw new IllegalArgumentException("Payment request must be in pending status before cancelling it !");
		
		record.entryOf(PaymentRequest::status, PaymentRequestStatus.CANCELLED)
		      .update();
	}

	@Override
	public Application application() throws IOException {
		return new PxApplication(record.of(PaymentRequest::application));
	}

	@Override
	public Currency currency() throws IOException {
		return order().currency();
	}
}
