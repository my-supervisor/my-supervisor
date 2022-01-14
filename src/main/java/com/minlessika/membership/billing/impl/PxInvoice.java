package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Invoice;
import com.minlessika.membership.billing.InvoiceState;
import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;
import java.time.LocalDate;

public final class PxInvoice extends PxOrder implements Invoice {

	private final Record<Invoice> record;
	
	public PxInvoice(Record<Invoice> record) throws IOException {
		super(record.of(Order.class, record.id()));
		
		this.record = record;
	}

	@Override
	public PurchaseOrder purchaseOrder() throws IOException {
		Record<PurchaseOrder> rec = record.valueOf(Invoice::purchaseOrder);
		return new PxPurchaseOrder(rec);
	}

	@Override
	public LocalDate dueDate() throws IOException {
		return record.valueOf(Invoice::dueDate);
	}

	@Override
	public InvoiceState state() throws IOException {
		return record.valueOf(Invoice::state);
	}
}
