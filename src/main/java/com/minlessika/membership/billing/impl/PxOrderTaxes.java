package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.OrderTax;
import com.minlessika.membership.billing.OrderTaxes;
import com.minlessika.membership.billing.Tax;
import com.minlessika.membership.billing.Taxes;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxOrderTaxes extends DomainRecordables<OrderTax, OrderTaxes> implements OrderTaxes {

	private final Order order;
	
	public PxOrderTaxes(Order order) throws IOException {
		this(order.listOf(OrderTax.class), order);
	}
	
	public PxOrderTaxes(RecordSet<OrderTax> source, Order order) throws IOException {
		super(PxOrderTax.class, source);
		
		this.order = order;
		this.source = source.where(OrderTax::order, order.id());
	}

	@Override
	public OrderTax add(Tax tax) throws IOException {
		
		source.mustBeUnique(OrderTax::origin, tax.id(), OrderTax::order, order.id());
		
		Record<OrderTax> record = source.entryOf(OrderTax::origin, tax.id())
				                        .entryOf(OrderTax::order, order.id())
				                        .entryOf(OrderTax::name, tax.toString())
				                        .entryOf(OrderTax::amount, 0)
				                        .add();
		
		return domainOf(record);
				
	}
	
	@Override
	protected OrderTaxes domainSetOf(final RecordSet<OrderTax> source) throws IOException {
		return new PxOrderTaxes(source, order);
	}

	@Override
	public void calculate() throws IOException {
						
		for (OrderTax tax : items()) {
			tax.calculate();
		}
	}

	@Override
	public double total() throws IOException {
		
		double total = 0;
		for (OrderTax tax : items()) {
			total += tax.amount();
		}
		
		return total;
	}

	@Override
	public void apply() throws IOException {
		
		remove(); // remove all taxes applied before
		
		if(!order.customerAddress().submittedToVat())
			return;
		
		Taxes taxes = new PxTaxes(source.of(Tax.class));
		for (Tax tax : taxes.items()) {
			add(tax);
		}
	}
}
