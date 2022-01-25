package com.supervisor.billing.impl;

import com.supervisor.billing.Order;
import com.supervisor.billing.OrderLine;
import com.supervisor.billing.OrderLines;
import com.supervisor.billing.Product;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxOrderLines extends DomainRecordables<OrderLine, OrderLines> implements OrderLines {

	private final Order order;
	
	public PxOrderLines(Order order) throws IOException {
		this(order.listOf(OrderLine.class), order);
	}
	
	public PxOrderLines(RecordSet<OrderLine> source, Order order) throws IOException {
		super(PxOrderLine.class, source);
		
		this.order = order;
		this.source = source.where(OrderLine::order, order.id())
				            .orderBy(OrderLine::no);
	}

	@Override
	protected OrderLines domainSetOf(final RecordSet<OrderLine> source) throws IOException {
		return new PxOrderLines(source, order);
	}
	
	@Override
	public OrderLine add(Product product, int quantity) throws IOException {
		return add(product, quantity, product.price());
	}

	@Override
	public OrderLine add(Product product, int quantity, double unitPrice) throws IOException {
		
		source.mustBeUnique(OrderLine::product, product.id(), OrderLine::order, order.id());
		
		Record<OrderLine> record = source.entryOf(OrderLine::product, product.id())
				                         .entryOf(OrderLine::order, order.id())
				                         .entryOf(OrderLine::no, isEmpty() ? 1 : last().no() + 1)
				                         .entryOf(OrderLine::quantity, quantity)
				                         .entryOf(OrderLine::baseUnitPrice, product.price())
				          		         .entryOf(OrderLine::unitPrice, unitPrice)
				          		         .entryOf(OrderLine::htAmount, quantity * unitPrice)
				                         .entryOf(OrderLine::name, product.name())
				                         .entryOf(OrderLine::description, product.description())
				                         .add();
		
		return domainOf(record);
	}

	@Override
	public double total() throws IOException {
		
		double total = 0;
		for (OrderLine line : items()) {
			total += line.htAmount();
		}
		
		return total;
	}

}
