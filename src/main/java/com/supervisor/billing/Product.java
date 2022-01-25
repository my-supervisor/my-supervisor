package com.supervisor.billing;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
		name="billing_product", 
		label="Produit"
)
public interface Product extends Recordable {
	
	@Field(
		label="Catalogue de produit",
		rel=Relation.MANY2ONE
	)
	ProductCatalog catalog() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Référence")
	String reference() throws IOException;
	
	@Field(label="Prix")
	double price() throws IOException;
	
	@Field(label="Activé")
	boolean enabled() throws IOException;
	
	@Field(label="Description", isMandatory=false)
	String description() throws IOException;
	
	void evaluate(double price) throws IOException;	
	void enable(boolean enabled) throws IOException;	
	void describe(String description) throws IOException;
}
