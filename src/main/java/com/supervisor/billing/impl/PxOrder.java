package com.supervisor.billing.impl;

import com.supervisor.billing.BillingAddress;
import com.supervisor.billing.Order;
import com.supervisor.billing.OrderLines;
import com.supervisor.billing.OrderTaxes;
import com.supervisor.billing.OrderType;
import com.supervisor.billing.PaymentReceipts;
import com.supervisor.billing.PaymentRequests;
import com.supervisor.domain.Currency;
import com.supervisor.domain.Person;
import com.supervisor.domain.impl.DmPerson;
import com.supervisor.domain.impl.PxCurrency;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;
import java.time.LocalDate;

public class PxOrder extends DomainRecordable<Order> implements Order {

	public PxOrder(Record<Order> record) throws IOException {
		super(record);
	}

	@Override
	public Person supplier() throws IOException {
		Record<Person> rec = record.of(Order::supplier);
		return new DmPerson(rec);
	}

	@Override
	public Person customer() throws IOException {
		Record<Person> rec = record.of(Order::customer);
		return new DmPerson(rec);
	}

	@Override
	public String reference() throws IOException {
		return record.valueOf(Order::reference);
	}

	@Override
	public LocalDate publishingDate() throws IOException {
		return record.valueOf(Order::publishingDate); 
	}

	@Override
	public double amount() throws IOException {
		return record.valueOf(Order::amount);
	}

	@Override
	public double vatAmount() throws IOException {
		return record.valueOf(Order::vatAmount);
	}

	@Override
	public double totalAmount() throws IOException {
		return record.valueOf(Order::totalAmount);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(Order::description);
	}

	@Override
	public OrderTaxes taxes() throws IOException {
		return new PxOrderTaxes(this);
	}

	@Override
	public void update(LocalDate publishingDate) throws IOException {
		
		record.entryOf(Order::publishingDate, publishingDate)
		      .update();
	}

	@Override
	public void describe(String description) throws IOException {
		record.entryOf(Order::description, description)
	          .update();
	}

	@Override
	public OrderLines lines() throws IOException {
		return new PxOrderLines(this);
	}

	@Override
	public Currency baseCurrency() throws IOException {
		Record<Currency> rec = record.of(Order::baseCurrency);
		return new PxCurrency(rec);
	}

	@Override
	public Currency currency() throws IOException {
		Record<Currency> rec = record.of(Order::currency);
		return new PxCurrency(rec);
	}

	@Override
	public double exchangeRate() throws IOException {
		return record.valueOf(Order::exchangeRate);
	}

	@Override
	public void calculate() throws IOException {
		
		taxes().calculate();
		
		final double htAmount = lines().total();
		final double vatAmount = taxes().total();
		final double ttcAmount = htAmount + vatAmount;
		
		record.entryOf(Order::amount, htAmount)
		      .entryOf(Order::vatAmount, vatAmount)
		      .entryOf(Order::totalAmount, ttcAmount)
		      .update();
	}

	@Override
	public OrderType type() throws IOException {
		return record.valueOf(Order::type);
	}

	@Override
	public PaymentReceipts receipts() throws IOException {
		return new PxPaymentReceipts(this);
	}

	@Override
	public BillingAddress supplierAddress() throws IOException {
		return new PxBillingAddress(record.of(Order::supplierAddress));
	}

	@Override
	public BillingAddress customerAddress() throws IOException {
		return new PxBillingAddress(record.of(Order::customerAddress));
	}

	@Override
	public PaymentRequests requests() throws IOException {
		return new PxPaymentRequests(this);
	}

}
