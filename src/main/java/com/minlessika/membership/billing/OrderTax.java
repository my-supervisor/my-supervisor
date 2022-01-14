package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		name="billing_order_tax", 
		label="Taxe d'un ordre"		
)
public interface OrderTax extends Recordable {
	
	@Field(
		label="Ordre",
		rel=Relation.MANY2ONE
	)
	Order order() throws IOException;
	
	@Field(
		label="Taxe d'origine",
		rel=Relation.MANY2ONE
	)
	Tax origin() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Montant")
	double amount() throws IOException;
	
	void calculate() throws IOException;
}
