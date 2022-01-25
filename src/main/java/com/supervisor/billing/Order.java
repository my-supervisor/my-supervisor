package com.supervisor.billing;

import com.supervisor.domain.Currency;
import com.supervisor.domain.Person;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDate;

@com.supervisor.sdk.metadata.Recordable(
	name="billing_order", 
	label="Ordre"
)
public interface Order extends Recordable {
	
	@Field(label="Type")
	OrderType type() throws IOException;
	
	@Field(
		label="Fournisseur",
		rel=Relation.MANY2ONE
	)
	Person supplier() throws IOException;
	
	@Field(
		label="Supplier address",
		rel=Relation.MANY2ONE
	)
	BillingAddress supplierAddress() throws IOException;
	
	@Field(
		label="Base currency",
		rel=Relation.MANY2ONE
	)
	Currency baseCurrency() throws IOException;
	
	@Field(
		label="Currency",
		rel=Relation.MANY2ONE
	)
	Currency currency() throws IOException;
	
	@Field(label="Exchange rate")
	double exchangeRate() throws IOException;
	
	@Field(
		label="Customer",
		rel=Relation.MANY2ONE
	)
	Person customer() throws IOException;	
	
	@Field(
		label="Customer address",
		rel=Relation.MANY2ONE
	)
	BillingAddress customerAddress() throws IOException;
	
	@Field(label="Reference", isMandatory=false)
	String reference() throws IOException;
	
	@Field(label="Edition date")
	LocalDate publishingDate() throws IOException;
	
	@Field(label="Amount")
	double amount() throws IOException;
	
	@Field(label="VAT amount")
	double vatAmount() throws IOException;
	
	@Field(label="Total amount")
	double totalAmount() throws IOException;
	
	@Field(label="Description", isMandatory=false)
	String description() throws IOException;
	
	OrderLines lines() throws IOException;
	OrderTaxes taxes() throws IOException;
	PaymentReceipts receipts() throws IOException;
	PaymentRequests requests() throws IOException;
	
	void update(LocalDate publishingDate) throws IOException;	
	void describe(String description) throws IOException;
	
	void calculate() throws IOException;
}
