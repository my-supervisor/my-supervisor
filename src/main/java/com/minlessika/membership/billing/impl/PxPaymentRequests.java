package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.billing.PaymentRequestStatus;
import com.minlessika.membership.billing.PaymentRequests;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Sequence;
import com.minlessika.membership.domain.impl.PxSequences;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;
import java.util.Map;

public final class PxPaymentRequests extends DomainRecordables<PaymentRequest, PaymentRequests> implements PaymentRequests {

	private final Order order;
	
	public PxPaymentRequests(final Order order) throws IOException {
		this(order.listOf(PaymentRequest.class), order);
	}
	
	public PxPaymentRequests(final RecordSet<PaymentRequest> source, final Order order) throws IOException {
		super(PxPaymentRequest.class, source);
		
		this.order = order;
		this.source = source.where(PaymentRequest::order, order.id())
				            .orderBy(PaymentRequest::id, OrderDirection.DESC);
	}

	@Override
	protected PaymentRequests domainSetOf(final RecordSet<PaymentRequest> source) throws IOException {
		return new PxPaymentRequests(source, order);
	}
	
	@Override
	public PaymentRequest add(String object, double amount, String notes, Map<String, String> metadata) throws IOException {
		
		source.isRequired(PaymentRequest::object, object);
		source.mustCheckThisCondition(amount >= 0, "Amount must be greater or equal to 0 !");
		
		if(!metadata.containsKey(Application.class.getSimpleName()))
			throw new IllegalArgumentException("Planned Task : Application ID must be specified in metadata !");
		
		final Sequence sequence = new PxSequences(source.of(Sequence.class)).get("PAYR");
		
		final Long applicationId = Long.parseLong(metadata.get(Application.class.getSimpleName()));
		Record<PaymentRequest> record = source.entryOf(PaymentRequest::order, order.id())
											   .entryOf(PaymentRequest::application, applicationId)
											   .entryOf(PaymentRequest::object, object)
										       .entryOf(PaymentRequest::amount, amount)
										       .entryOf(PaymentRequest::metadata, metadata)
										       .entryOf(PaymentRequest::notes, notes)
										       .entryOf(PaymentRequest::status, PaymentRequestStatus.PENDING)
										       .entryOf(PaymentRequest::reference, sequence.generate())
										       .add();
		
		return domainOf(record);
	}
}
