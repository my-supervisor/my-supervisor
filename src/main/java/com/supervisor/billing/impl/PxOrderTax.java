package com.supervisor.billing.impl;

import com.supervisor.billing.Order;
import com.supervisor.billing.OrderTax;
import com.supervisor.billing.Tax;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import org.apache.commons.math3.util.Precision;

import java.io.IOException;

public final class PxOrderTax extends DomainRecordable<OrderTax> implements OrderTax {

	public PxOrderTax(Record<OrderTax> record) throws IOException {
		super(record);
	}

	@Override
	public Order order() throws IOException {
		Record<Order> rec = record.of(OrderTax::order);
		return new PxOrder(rec);
	}

	@Override
	public Tax origin() throws IOException {
		Record<Tax> rec = record.of(OrderTax::origin);
		return new PxTax(rec);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(OrderTax::name);
	}

	@Override
	public double amount() throws IOException {
		return record.valueOf(OrderTax::amount);
	}

	@Override
	public void calculate() throws IOException {
		
		final Tax tax = origin();
		final int precision = order().currency().precision();		
		
		final double amount;		
		switch (tax.valueType()) {
			case PERCENT:
				final double htAmount = order().lines().total();
				final double decimalValue = tax.decimalValue();
				amount = Precision.round(htAmount * decimalValue, precision);
				break;
			case AMOUNT:
				amount = Precision.round(tax.value(), precision);
				break;
			default:
				throw new IllegalArgumentException(String.format("Le type de valeur %s n'est pas pris en charge !", tax.valueType().toString()));
		}
		
		record.entryOf(OrderTax::amount, amount)
		      .update();
	}

}
