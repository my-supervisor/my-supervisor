package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface FinancialOrders extends DomainSet<FinancialOrder, FinancialOrders> {
	FinancialOrder add(String description) throws IOException;
}
