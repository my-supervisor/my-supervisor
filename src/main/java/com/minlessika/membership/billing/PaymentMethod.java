package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Person;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		name="billing_payment_method", 
		label="Mode de paiement"
)
public interface PaymentMethod extends Recordable {

	@Field(
		label="Caissier par défaut",
		rel=Relation.MANY2ONE
	)
	Person defaultCashier() throws IOException;
	
	@Field(label="Type")
	PaymentType type() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Activé")
	boolean enabled() throws IOException;
	
	@Field(label="En ligne")
	boolean online() throws IOException;
	
	@Field(label="Logo")
	String logo() throws IOException;
	
	@Field(label="Pseudo", isMandatory=false)
	String username() throws IOException;
	
	@Field(label="Mot de passe", isMandatory=false)
	String password() throws IOException;
	
	void enable(boolean enabled) throws IOException;
	void rename(String newName) throws IOException;
	
	void changeCredentials(String username, String password) throws IOException;	
	void complete(PaymentReceipt payment) throws IOException;
	
	PaymentMethodReceipts payments() throws IOException;
	PaymentReceipt payment(String token) throws IOException;	
	PaymentReceiptState check(PaymentReceipt payment) throws IOException;
	
	PaymentMethodReceipts pendingPayments() throws IOException;
}
