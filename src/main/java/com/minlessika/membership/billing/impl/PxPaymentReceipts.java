package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.billing.PaymentReceipts;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.domain.Person;
import com.minlessika.membership.domain.Sequence;
import com.minlessika.membership.domain.impl.PxSequences;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.comparators.Matchers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public final class PxPaymentReceipts extends DomainRecordables<PaymentReceipt, PaymentReceipts> implements PaymentReceipts {

	private final Order order;
	
	public PxPaymentReceipts(Order order) throws IOException {
		this(order.listOf(PaymentReceipt.class), order);
	}
	
	public PxPaymentReceipts(RecordSet<PaymentReceipt> source, Order order) throws IOException {
		super(PxPaymentReceipt.class, source);
		
		this.order = order;
		this.source = source.where(PaymentReceipt::order, order.id())
	                        .orderBy(PaymentReceipt::creationDate);
	}

	@Override
	protected PaymentReceipts domainSetOf(final RecordSet<PaymentReceipt> source) throws IOException {
		return new PxPaymentReceipts(source, order);
	}

	@Override
	public PaymentReceipt add(PaymentMethod method, PaymentRequest request, String object, Map<String, String> metadata) throws IOException {
		
		if(!order.id().equals(request.order().id()))
			throw new IllegalArgumentException("Generation of receipt: The request payment doesn't match with order !");
		
		final Person customer = order.customer();
		Sequence sequence = new PxSequences(source.of(Sequence.class)).get("PAY");
		
		final Record<PaymentReceipt> record = 
				source.entryOf(PaymentReceipt::remitter, customer.id())
		                .entryOf(PaymentReceipt::cashier, method.defaultCashier().id())
		                .entryOf(PaymentReceipt::order, order.id())
		                .entryOf(PaymentReceipt::request, request.id())
		                .entryOf(PaymentReceipt::reference, sequence.generate())
		                .entryOf(PaymentReceipt::object, object)
		                .entryOf(PaymentReceipt::paymentDate, LocalDate.now())
		                .entryOf(PaymentReceipt::amount, request.amount())
		                .entryOf(PaymentReceipt::method, method.id())
		                .entryOf(PaymentReceipt::state, PaymentReceiptState.PENDING)
		                .entryOf(PaymentReceipt::metadata, metadata) 
		                .add();
		
		return domainOf(record);
	}
	
	@Override
	public boolean contains(String token) throws IOException {
		return where(PaymentReceipt::metadata, Matchers.contains(token)).any();
	}

	@Override
	public PaymentReceipt get(String token) throws IOException {
		return where(PaymentReceipt::metadata, Matchers.contains(token)).first();
	}
}

