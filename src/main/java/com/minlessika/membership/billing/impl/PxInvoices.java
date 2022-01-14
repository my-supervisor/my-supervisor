package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Invoice;
import com.minlessika.membership.billing.InvoiceState;
import com.minlessika.membership.billing.Invoices;
import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.OrderLine;
import com.minlessika.membership.billing.OrderType;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.domain.Sequence;
import com.minlessika.membership.domain.impl.PxSequences;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;
import java.time.LocalDate;

public final class PxInvoices extends DomainRecordables<Invoice, Invoices> implements Invoices {

	public PxInvoices(RecordSet<Invoice> source) {
		super(PxInvoice.class, source);
	}

	@Override
	public Invoice makeInvoiceOf(PurchaseOrder order) throws IOException {
		
		Sequence sequence = new PxSequences(source.of(Sequence.class)).get("INV");
		
		Record<Order> recOrder = source.of(Order.class)
				   .entryOf(Order::reference, sequence.generate())
				   .entryOf(Order::publishingDate, LocalDate.now())
				   .entryOf(Order::customer, order.customer().id())
				   .entryOf(Order::supplier, order.supplier().id())
				   .entryOf(Order::customerAddress, order.customerAddress().id())
				   .entryOf(Order::supplierAddress, order.supplierAddress().id())
				   .entryOf(Order::baseCurrency, order.baseCurrency().id()) // Euro
				   .entryOf(Order::currency, order.currency().id())
				   .entryOf(Order::exchangeRate, order.exchangeRate())
				   .entryOf(Order::amount, 0)
				   .entryOf(Order::vatAmount, 0)
				   .entryOf(Order::totalAmount, 0)
				   .entryOf(Order::description, order.description())
				   .entryOf(Order::type, OrderType.INVOICE)
				   .add();

		Record<Invoice> record = source.entryOf(Invoice::id, recOrder.id())
				                       .entryOf(Invoice::purchaseOrder, order.id())
									   .entryOf(Invoice::dueDate, LocalDate.now())
				                       .entryOf(Invoice::state, InvoiceState.PAID)
				                       .add();
		
		Invoice invoice = domainOf(record);
		
		for (OrderLine line : order.lines().items()) {
			invoice.lines().add(line.product(), line.quantity(), line.unitPrice());
		}
		
		invoice.taxes().apply();
		invoice.calculate();
		
		return invoice;
	}

}
