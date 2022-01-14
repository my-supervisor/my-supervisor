package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.Person;
import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface PurchaseOrders extends DomainSet<PurchaseOrder, PurchaseOrders> {
	PurchaseOrder add(Person customer, String description) throws IOException;
	PurchaseOrder add(Person customer, Currency currency, String description) throws IOException;
}
