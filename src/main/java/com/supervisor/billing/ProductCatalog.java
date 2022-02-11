package com.supervisor.billing;

import com.supervisor.domain.Currency;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
	name="product_catalog",
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
