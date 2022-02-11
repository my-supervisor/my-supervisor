package com.supervisor.billing;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

import java.io.IOException;

@Recordable(
		name="purchase_order",
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
