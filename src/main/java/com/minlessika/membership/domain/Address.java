package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		name="base_address", 
		label="Address"
)
public interface Address extends Recordable {
	
	@Field(label="Email address")
	String email() throws IOException;
	
	@Field(
		label="Country",
		rel=Relation.MANY2ONE
	)
	Country country() throws IOException;
	
	@Field(label="Address line 1", isMandatory=false)
	String addressLine1() throws IOException;
	
	@Field(label="Address line 2", isMandatory=false)
	String addressLine2() throws IOException;
	
	@Field(label="Phone 1", isMandatory=false)
	String phone1() throws IOException;
	
	@Field(label="Phone 2", isMandatory=false)
	String phone2() throws IOException;
	
	@Field(label="City", isMandatory=false)
	String city() throws IOException;
	
	@Field(label="State/Province", isMandatory=false)
	String stateOrProvince() throws IOException;
	
	@Field(label="Company", isMandatory=false)
	String company() throws IOException;
	
	void defineCompany(String company) throws IOException;
	void defineAddress(String addressLine1, String addressLine2, String email) throws IOException;
	void definePhone(String phone1, String phone2) throws IOException;
	void defineLocation(String city, String stateOrProvince, Country country) throws IOException;	
	
	boolean isComplete() throws IOException;
}
