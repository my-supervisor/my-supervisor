package com.supervisor.billing.impl;

import com.supervisor.billing.Invoice;
import com.supervisor.billing.Order;
import com.supervisor.billing.OrderType;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.billing.PaymentReceipts;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.billing.PurchaseOrderState;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxPurchaseOrder extends PxOrder implements PurchaseOrder {

	private final Record<PurchaseOrder> record;
	
	public PxPurchaseOrder(final Order order) throws IOException {
		super(order.listOf(Order.class).get(order.id()));
		
		if(order.type() != OrderType.PURCHASE_ORDER) 
			throw new IllegalArgumentException(String.format("We are waiting for %s and got %s !", OrderType.PURCHASE_ORDER.toString(), order.type().toString()));
		
		this.record = order.listOf(PurchaseOrder.class).get(order.id());
	}
	
	public PxPurchaseOrder(Record<PurchaseOrder> record) throws IOException {
		super(record.listOf(Order.class).get(record.id()));
		
		this.record = record;
	}

	@Override
	public PurchaseOrderState state() throws IOException {
		return record.valueOf(PurchaseOrder::state);
	}

	@Override
	public Invoice generateInvoice() throws IOException {
		return new PxInvoices(record.listOf(Invoice.class)).makeInvoiceOf(this);
	}

	@Override
	public void confirm() throws IOException {
		
		if(state() != PurchaseOrderState.PENDING)
			throw new IllegalArgumentException("You can only confirm an order in PENDING status !");
		
		record.entryOf(PurchaseOrder::state, PurchaseOrderState.CONFIRMED)
		      .update();
	}

	@Override
	public PaymentReceipts receipts() throws IOException {
		return new PxPaymentReceipts(this);
	}

	@Override
	public void cancel() throws IOException {
		
		if(state() != PurchaseOrderState.PENDING)
			throw new IllegalArgumentException("You can only cancel an order in PENDING status !");
		
		record.entryOf(PurchaseOrder::state, PurchaseOrderState.CANCELLED)
		      .update();
	}

	@Override
	public void complete() throws IOException {
		if(!(state() == PurchaseOrderState.PENDING || state() == PurchaseOrderState.CONFIRMED))
			throw new IllegalArgumentException("You can't complete this order !");
		
		record.entryOf(PurchaseOrder::state, PurchaseOrderState.PAID)
		      .update();
	}

	@Override
	public double paidAmount() throws IOException {
		double sum = 0;
		for (final PaymentReceipt receipt : receipts().where(PaymentReceipt::state, PaymentReceiptState.CONFIRMED).items()) {
			sum += receipt.amount();
		}
		
		return sum;
	}
}
