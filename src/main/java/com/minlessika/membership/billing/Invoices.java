package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Invoices extends DomainSet<Invoice, Invoices> {
	Invoice makeInvoiceOf(PurchaseOrder order) throws IOException;
}
