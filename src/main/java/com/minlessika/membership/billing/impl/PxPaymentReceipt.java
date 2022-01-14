package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.Person;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmPerson;
import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class PxPaymentReceipt extends DomainRecordable<PaymentReceipt> implements PaymentReceipt {

	public PxPaymentReceipt(Record<PaymentReceipt> record) throws IOException {
		super(record);
	}

	@Override
	public Person remitter() throws IOException {
		Record<Person> rec = record.of(PaymentReceipt::remitter);
		return new DmPerson(rec);
	}

	@Override
	public Person cashier() throws IOException {
		Record<Person> rec = record.of(PaymentReceipt::cashier);
		return new DmPerson(rec);
	}

	@Override
	public String reference() throws IOException {
		return record.valueOf(PaymentReceipt::reference);
	}

	@Override
	public String object() throws IOException {
		return record.valueOf(PaymentReceipt::object);
	}

	@Override
	public LocalDate paymentDate() throws IOException {
		return record.valueOf(PaymentReceipt::paymentDate);
	}

	@Override
	public double amount() throws IOException {
		return record.valueOf(PaymentReceipt::amount);
	}

	@Override
	public PaymentMethod method() throws IOException {
		Record<PaymentMethod> rec = record.of(PaymentReceipt::method);
		return new PxPaymentMethod(rec);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(PaymentReceipt::description);
	}

	@Override
	public PaymentReceiptState state() throws IOException {
		return record.valueOf(PaymentReceipt::state);
	}

	@Override
	public Order order() throws IOException {
		Record<Order> rec = record.of(PaymentReceipt::order);
		return new PxOrder(rec);
	}

	@Override
	public void confirm() throws IOException {

		if(state() != PaymentReceiptState.PENDING)
			throw new IllegalArgumentException("Vous ne pouvez confirmer qu'un paiement en attente de confirmation !");
		
		record.entryOf(PaymentReceipt::state, PaymentReceiptState.CONFIRMED)
		      .update();
		
		request().accept();
		
		final User user = new OwnerOf(this);
		
		// create an task to execute for application
		final Map<String, String> metadata = request().metadata();
		metadata.put(PaymentReceipt.class.getSimpleName(), id().toString());
		metadata.put(PaymentRequest.class.getSimpleName(), request().id().toString());

		user.plannedTasks().planDirectTask(metadata, String.format("Payment %s achieved.", reference()));
	}

	@Override
	public void cancel() throws IOException {
		
		if(state() == PaymentReceiptState.CONFIRMED)
			throw new IllegalArgumentException("Vous ne pouvez annuler un paiement déjà confirmé !");
		
		record.entryOf(PaymentReceipt::state, PaymentReceiptState.CANCELLED)
		      .update();
	}

	@Override
	public PaymentRequest request() throws IOException {
		return new PxPaymentRequest(record.of(PaymentReceipt::request));
	}

	@Override
	public Map<String, String> metadata() throws IOException {
		return record.valueOf(PaymentReceipt::metadata);
	}

	@Override
	public void updateMetadata(Map<String, String> metadata) throws IOException {
		record.entryOf(PaymentReceipt::metadata, metadata)
		      .update();
	}

	@Override
	public Currency currency() throws IOException {
		return order().currency();
	}
}
