package com.minlessika.membership.billing;

import com.minlessika.sdk.metadata.Field;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	name="billing_financial_order", 
	label="Financial order",
	comodel=Order.class
)
public interface FinancialOrder extends Order {
	
	@Field(label="Status")
	FinancialOrderState status() throws IOException;
	
	@Field(label="Nature")
	FinancialOrderNature nature() throws IOException;
	
	void complete() throws IOException;
	void cancel() throws IOException;
}
