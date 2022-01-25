package com.supervisor.billing.impl;

import com.supervisor.billing.BillingAddress;
import com.supervisor.billing.BillingAddresses;
import com.supervisor.domain.Address;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.UserAdmin;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxBillingAddresses extends DomainRecordables<BillingAddress, BillingAddresses> implements BillingAddresses {

	public PxBillingAddresses(RecordSet<BillingAddress> source) {
		super(PxBillingAddress.class, source);
	}

	@Override
	public BillingAddress add(Address address) throws IOException {
		
		if(contains(address.id()))
			return new PxBillingAddress(address);
		
		final User minlessika = new UserAdmin(base());
		boolean submittedToVAT = false;
		if(minlessika.address().country().equals(address.country())) {
			submittedToVAT = true;
		}
		
		Record<BillingAddress> record = source.entryOf(BillingAddress::id, address.id())
											  .entryOf(BillingAddress::submittedToVat, submittedToVAT)
				                              .add();
		return domainOf(record);
	}
}
