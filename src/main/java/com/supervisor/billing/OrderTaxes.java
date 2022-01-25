package com.supervisor.billing;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface OrderTaxes extends DomainSet<OrderTax, OrderTaxes> {
	OrderTax add(Tax tax) throws IOException;
	void apply() throws IOException;
	void calculate() throws IOException;
	
	double total() throws IOException;
}
