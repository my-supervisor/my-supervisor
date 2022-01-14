package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Address;
import com.minlessika.sdk.metadata.Field;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		name="billing_address", 
		label="Billing address",
		comodel=Address.class
)
public interface BillingAddress extends Address {
	
	@Field(label="Submitted to VAT")
	boolean submittedToVat() throws IOException;
	
	void changeVATStatus(boolean submitted) throws IOException;		
}
