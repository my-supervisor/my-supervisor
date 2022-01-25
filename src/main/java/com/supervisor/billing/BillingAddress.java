package com.supervisor.billing;

import com.supervisor.domain.Address;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

import java.io.IOException;

@Recordable(
		name="billing_address", 
		label="Billing address",
		comodel=Address.class
)
public interface BillingAddress extends Address {
	
	@Field(label="Submitted to VAT")
	boolean submittedToVat() throws IOException;
	
	void changeVATStatus(boolean submitted) throws IOException;		
}
