package com.supervisor.billing;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

import java.io.IOException;

@Recordable(
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
