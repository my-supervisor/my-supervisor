package com.minlessika.membership.billing;

import com.minlessika.sdk.metadata.Field;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	name="billing_software_engineering_service", 
	label="Software engineering service",
	comodel=Product.class
)
public interface SoftwareEngineeringService extends Product {	
	
	@Field(label="Ordre", name="no")
	int no() throws IOException;
}
