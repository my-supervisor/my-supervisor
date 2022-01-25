package com.supervisor.billing.impl;

import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.billing.PaymentReceipts;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.domain.Person;
import com.supervisor.domain.Sequence;
import com.supervisor.domain.impl.PxSequences;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Matchers;

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

