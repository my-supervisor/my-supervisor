package com.supervisor.billing;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Invoices extends DomainSet<Invoice, Invoices> {
	Invoice makeInvoiceOf(PurchaseOrder order) throws IOException;
}
