package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface OrderLines extends DomainSet<OrderLine, OrderLines> {
	
	OrderLine add(Product product, int quantity) throws IOException;
	OrderLine add(Product product, int quantity, double unitPrice) throws IOException;
	
	double total() throws IOException;
}
