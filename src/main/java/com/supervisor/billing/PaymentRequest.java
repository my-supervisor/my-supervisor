package com.supervisor.billing;

import com.supervisor.domain.Application;
import com.supervisor.domain.Currency;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;
import java.util.Map;

@com.supervisor.sdk.metadata.Recordable(
		name="billing_payment_request", 
		label="Payment request"
)
public interface PaymentRequest extends Recordable {
	
	@Field(
		label="Application",
		rel=Relation.MANY2ONE
	)
	Application application() throws IOException;
	
	@Field(
		label="Order",
		rel=Relation.MANY2ONE
	)
	Order order() throws IOException;
	
	@Field(label="Reference")
	String reference() throws IOException;
	
	@Field(label="Object")
	String object() throws IOException;
	
	@Field(label="Amount")
	double amount() throws IOException;
	
	Currency currency() throws IOException;
	
	@Field(label="Notes", isMandatory=false)
	String notes() throws IOException;
	
	@Field(label="Status")
	PaymentRequestStatus status() throws IOException;
	
	@Field(label="Metadata")
	Map<String, String> metadata() throws IOException;
	
	void accept() throws IOException;
	void cancel() throws IOException;
}
