package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Address;
import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface BillingAddresses extends DomainSet<BillingAddress, BillingAddresses> {
	BillingAddress add(Address address) throws IOException;
}
