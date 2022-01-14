package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Currency;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;
import java.util.Map;

@com.minlessika.sdk.metadata.Recordable(
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
