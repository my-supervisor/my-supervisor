package com.supervisor.billing;

import com.supervisor.domain.Person;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
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
