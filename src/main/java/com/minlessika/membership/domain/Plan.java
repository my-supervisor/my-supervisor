package com.minlessika.membership.domain;

import com.minlessika.membership.billing.Product;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	name="membership_plan", 
	label="Mode de tarification",
	comodel=Product.class
)
public interface Plan extends Product {	
	
	@Field(
		label="Profil",
		rel=Relation.MANY2ONE
	)
	Profile profile() throws IOException;
	
	@Field(label="Ordre", name="no")
	int order() throws IOException;
	
	@Field(label="Préféré")
	boolean preferred() throws IOException;
	
	void organize(int order) throws IOException;
	void prefer(boolean preferred) throws IOException;
	
	PlanFeatures features() throws IOException;
}
