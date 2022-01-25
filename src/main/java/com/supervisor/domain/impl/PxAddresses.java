package com.supervisor.domain.impl;

import com.supervisor.domain.Address;
import com.supervisor.domain.Addresses;
import com.supervisor.domain.Country;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

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
