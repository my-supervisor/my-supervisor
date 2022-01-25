package com.supervisor.billing.impl;

import com.supervisor.billing.Invoice;
import com.supervisor.billing.InvoiceState;
import com.supervisor.billing.Order;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.sdk.datasource.Record;

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
