package com.supervisor.billing;

import com.supervisor.domain.Person;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDate;

@com.supervisor.sdk.metadata.Recordable(
	name="billing_subscription_contract", 
	label="Contrat d'abonnement"
)
public interface SubscriptionContract extends Recordable {
	
	@Field(
		label="Abonné",
		rel=Relation.MANY2ONE
	)
	Person subscriber() throws IOException;
		
	@Field(
		label="Bon de commande",
		rel=Relation.MANY2ONE
	)
	PurchaseOrder purchaseOrder() throws IOException;
	
	@Field(
		label="Facture",
		rel=Relation.MANY2ONE,
		isMandatory=false
	)
	Invoice invoice() throws IOException;
	
	@Field(label="Date de début")
	LocalDate beginDate() throws IOException;
	
	@Field(label="Date de fin")
	LocalDate endDate() throws IOException;
}
