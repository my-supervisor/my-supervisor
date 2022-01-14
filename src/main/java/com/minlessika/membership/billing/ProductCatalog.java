package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Currency;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	name="billing_product_catalog", 
	label="Catalogue de produits"
)
public interface ProductCatalog extends Recordable {
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(
		label="Devise",
		rel=Relation.MANY2ONE
	)
	Currency currency() throws IOException;
	
	Products products() throws IOException;
}
