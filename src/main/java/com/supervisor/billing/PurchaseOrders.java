package com.supervisor.billing;

import com.supervisor.domain.Currency;
import com.supervisor.domain.Person;
import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface PurchaseOrders extends DomainSet<PurchaseOrder, PurchaseOrders> {
	PurchaseOrder add(Person customer, String description) throws IOException;
	PurchaseOrder add(Person customer, Currency currency, String description) throws IOException;
}
