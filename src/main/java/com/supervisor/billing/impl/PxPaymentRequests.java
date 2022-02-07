package com.supervisor.billing.impl;

import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.billing.PaymentRequestStatus;
import com.supervisor.billing.PaymentRequests;
import com.supervisor.domain.Sequence;
import com.supervisor.domain.impl.PxSequences;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
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
		
		final Sequence sequence = new PxSequences(source.of(Sequence.class)).get("PAYR");

		Record<PaymentRequest> record = source.entryOf(PaymentRequest::order, order.id())
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
