package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Address;
import com.minlessika.membership.domain.Addresses;
import com.minlessika.membership.domain.Country;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxAddresses extends DomainRecordables<Address, Addresses> implements Addresses {

	public PxAddresses(RecordSet<Address> source) {
		super(PxAddress.class, source);
	}

	@Override
	public Address add(String email, Country country) throws IOException {
		
		source.isRequired(Address::email, email);
		
		Record<Address> record = source.entryOf(Address::email, email.toLowerCase())
									   .entryOf(Address::country, country.id())
				                       .add();
		return domainOf(record);
	}
}
