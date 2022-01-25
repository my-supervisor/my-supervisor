package com.supervisor.billing;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

import java.io.IOException;

@Recordable(
	name="billing_software_engineering_service", 
	label="Software engineering service",
	comodel=Product.class
)
public interface SoftwareEngineeringService extends Product {	
	
	@Field(label="Ordre", name="no")
	int no() throws IOException;
}
