package com.supervisor.billing;

import com.supervisor.domain.Currency;
import com.supervisor.domain.Person;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@com.supervisor.sdk.metadata.Recordable(
	name="billing_payment_receipt", 
	label="Bill"
)
public interface PaymentReceipt extends Recordable {
	
	@Field(
		label="Payment request",
		rel=Relation.MANY2ONE
	)
	PaymentRequest request() throws IOException;
	
	@Field(
		label="Order",
		rel=Relation.MANY2ONE
	)
	Order order() throws IOException;
	
	@Field(
		label="Remettant",
		rel=Relation.MANY2ONE
	)
	Person remitter() throws IOException;	
	
	@Field(
		label="Caissier",
		rel=Relation.MANY2ONE
	)
	Person cashier() throws IOException;
	
	@Field(label="Référence")
	String reference() throws IOException;
	
	@Field(label="Objet")
	String object() throws IOException;
	
	@Field(label="Date de paiement")
	LocalDate paymentDate() throws IOException;
	
	@Field(label="Montant")
	double amount() throws IOException;
	
	Currency currency() throws IOException;
	
	@Field(
		label="Mode de paiement",
		rel=Relation.MANY2ONE
	)
	PaymentMethod method() throws IOException;
	
	@Field(label="Notes", isMandatory=false)
	String description() throws IOException;
	
	@Field(label="Metadata")
	Map<String, String> metadata() throws IOException;	
	
	@Field(label="Etat")
	PaymentReceiptState state() throws IOException;
	
	void confirm() throws IOException;
	void cancel() throws IOException;
	void updateMetadata(Map<String, String> metadata) throws IOException;
}
