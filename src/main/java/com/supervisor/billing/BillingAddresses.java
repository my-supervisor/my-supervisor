package com.supervisor.billing;

import com.supervisor.domain.Address;
import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface BillingAddresses extends DomainSet<BillingAddress, BillingAddresses> {
	BillingAddress add(Address address) throws IOException;
}
