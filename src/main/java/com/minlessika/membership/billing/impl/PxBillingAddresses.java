package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.BillingAddress;
import com.minlessika.membership.billing.BillingAddresses;
import com.minlessika.membership.domain.Address;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.UserAdmin;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

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
