package com.supervisor.billing;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
		name="order_tax",
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
