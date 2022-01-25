package com.supervisor.billing.impl;

import com.supervisor.billing.Invoice;
import com.supervisor.billing.InvoiceState;
import com.supervisor.billing.Invoices;
import com.supervisor.billing.Order;
import com.supervisor.billing.OrderLine;
import com.supervisor.billing.OrderType;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.domain.Sequence;
import com.supervisor.domain.impl.PxSequences;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

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
