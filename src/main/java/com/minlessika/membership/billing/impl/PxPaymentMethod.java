package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.OrderType;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentMethodReceipts;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.billing.PaymentType;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.domain.Person;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmPerson;
import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.comparators.Matchers;

import java.io.IOException;

public class PxPaymentMethod extends DomainRecordable<PaymentMethod> implements PaymentMethod {

	public PxPaymentMethod(Record<PaymentMethod> record) throws IOException {
		super(record);
	}

	@Override
	public PaymentType type() throws IOException {
		return record.valueOf(PaymentMethod::type);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(PaymentMethod::name);
	}

	@Override
	public boolean enabled() throws IOException {
		return record.valueOf(PaymentMethod::enabled);
	}

	@Override
	public boolean online() throws IOException {
		return record.valueOf(PaymentMethod::online);
	}

	@Override
	public String logo() throws IOException {
		return record.valueOf(PaymentMethod::logo);
	}

	@Override
	public void enable(boolean enabled) throws IOException {
		record.entryOf(PaymentMethod::enabled, enabled)
		      .update();
	}

	@Override
	public void rename(String newName) throws IOException {
		
		record.isRequired(PaymentMethod::name, newName);
		
		record.entryOf(PaymentMethod::name, newName)
	          .update();		
	}

	@Override
	public Person defaultCashier() throws IOException {
		Record<Person> rec = record.of(PaymentMethod::defaultCashier);
		return new DmPerson(rec);
	}

	@Override
	public String username() throws IOException {
		return record.valueOf(PaymentMethod::username);
	}

	@Override
	public String password() throws IOException {
		return record.valueOf(PaymentMethod::password);
	}
	
	@Override
	public void changeCredentials(String username, String password) throws IOException {
		
		record.isRequired(PaymentMethod::username, username);
		record.isRequired(PaymentMethod::password, password);
		
		record.entryOf(PaymentMethod::username, username)
		      .entryOf(PaymentMethod::password, password)
		      .update();
	}
	
	@Override
	public void complete(PaymentReceipt payment) throws IOException {
		if (payment.state() != PaymentReceiptState.PENDING)
			throw new IllegalArgumentException("Le paiement ne peut pas être finalisé !");
			
		final OrderType orderType = payment.order().type();
		if(orderType == OrderType.PURCHASE_ORDER) {
			PurchaseOrder order = new PxPurchaseOrder(payment.order());		
			User user = new OwnerOf(order);
			if(user.isCompany()) {
				// créer une facture
				order.generateInvoice();
			}
			
			if(order.totalAmount() == order.paidAmount()) {
				order.complete();
			}
			
		} else {
			throw new IllegalArgumentException(String.format("%s order not supported !", orderType.toString()));
		}
								
		payment.confirm();
	}
	
	@Override
	public PaymentReceipt payment(String key) throws IOException {
		
		RecordSet<PaymentReceipt> receiptSource = record.listOf(PaymentReceipt.class)
				                                        .where(PaymentReceipt::metadata, Matchers.contains(key));
		
		if(receiptSource.isEmpty())
			throw new IllegalArgumentException(String.format("La transaction (key : %s) n'a pas été retrouvée !", key)); 
		
		return new PxPaymentReceipt(receiptSource.first());
	}

	@Override
	public PaymentReceiptState check(PaymentReceipt payment) throws IOException {
		throw new UnsupportedOperationException("PxPaymentMethod#check");
	}
	
	@Override
	public PaymentMethodReceipts payments() throws IOException {
		return new PgPaymentMethodReceipts(this);
	}

	@Override
	public PaymentMethodReceipts pendingPayments() throws IOException {
		return payments().where(PaymentReceipt::state, PaymentReceiptState.PENDING);
	}	
}
