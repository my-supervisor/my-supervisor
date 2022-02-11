package com.supervisor.billing;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
	name="order_line",
	label="Ligne d'un ordre"
)
public interface OrderLine extends Recordable {
	
	@Field(
		label="Ordre",
		rel=Relation.MANY2ONE
	)
	Order order() throws IOException;
	
	@Field(
		label="Produit",
		rel=Relation.MANY2ONE
	)
	Product product() throws IOException;
	
	@Field(label="Name")
	String name() throws IOException;
	
	@Field(label="Description", isMandatory=false)
	String description() throws IOException;
	
	@Field(label="N° d'ordre")
	int no() throws IOException;
	
	@Field(label="Quantité")
	int quantity() throws IOException;
	
	@Field(label="Prix unitaire de base")
	double baseUnitPrice() throws IOException;
	
	@Field(label="Prix unitaire")
	double unitPrice() throws IOException;
	
	@Field(label="Montant HT")
	double htAmount() throws IOException;
	
	void rename(String newName) throws IOException;
	void describe(String description) throws IOException;
	void update(int quantity, double unitPrice) throws IOException;
}
