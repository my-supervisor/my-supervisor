package com.minlessika.membership.billing;

import com.minlessika.sdk.metadata.Field;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		name="billing_purchase_order", 
		label="Bon de commande",
		comodel=Order.class
)
public interface PurchaseOrder extends Order {
	
	@Field(label="Etat")
	PurchaseOrderState state() throws IOException;
	
	Invoice generateInvoice() throws IOException;
	
	double paidAmount() throws IOException;
	
	void confirm() throws IOException;
	void complete() throws IOException;
	void cancel() throws IOException;
}
