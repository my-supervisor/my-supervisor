package com.supervisor.billing;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface FinancialOrders extends DomainSet<FinancialOrder, FinancialOrders> {
	FinancialOrder add(String description) throws IOException;
}
