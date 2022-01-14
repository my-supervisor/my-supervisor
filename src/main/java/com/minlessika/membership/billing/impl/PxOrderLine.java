package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.OrderLine;
import com.minlessika.membership.billing.Product;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;

public final class PxOrderLine extends DomainRecordable<OrderLine> implements OrderLine {

	public PxOrderLine(Record<OrderLine> record) throws IOException {
		super(record);
	}

	@Override
	public Order order() throws IOException {
		Record<Order> rec = record.of(OrderLine::order);
		return new PxOrder(rec);
	}

	@Override
	public Product product() throws IOException {
		return new ProductBase(record.of(OrderLine::product));
	}

	@Override
	public int no() throws IOException {
		return record.valueOf(OrderLine::no);
	}

	@Override
	public int quantity() throws IOException {
		return record.valueOf(OrderLine::quantity);
	}

	@Override
	public double unitPrice() throws IOException {
		return record.valueOf(OrderLine::unitPrice);
	}

	@Override
	public double htAmount() throws IOException {
		return record.valueOf(OrderLine::htAmount);
	}

	@Override
	public void update(int quantity, double unitPrice) throws IOException {
		
		record.entryOf(OrderLine::quantity, quantity)
		      .entryOf(OrderLine::unitPrice, unitPrice)
		      .entryOf(OrderLine::htAmount, unitPrice * quantity)
		      .update();		
	}

	@Override
	public double baseUnitPrice() throws IOException {
		return record.valueOf(OrderLine::baseUnitPrice);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(OrderLine::name);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(OrderLine::description);
	}

	@Override
	public void rename(String newName) throws IOException {
		
		record.isRequired(OrderLine::name, newName);
		
		record.entryOf(OrderLine::name, newName)
		      .update();
	}

	@Override
	public void describe(String description) throws IOException {
		
		record.entryOf(OrderLine::description, description)
	      	  .update();
	}
}
