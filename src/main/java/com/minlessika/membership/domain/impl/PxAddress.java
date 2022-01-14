package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Address;
import com.minlessika.membership.domain.Country;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public final class PxAddress extends DomainRecordable<Address> implements Address {

	public PxAddress(Record<Address> record) {
		super(record);
	}

	@Override
	public String addressLine1() throws IOException {
		return record.valueOf(Address::addressLine1);
	}

	@Override
	public String addressLine2() throws IOException {
		return record.valueOf(Address::addressLine2);
	}

	@Override
	public String phone1() throws IOException {
		return record.valueOf(Address::phone1);
	}

	@Override
	public String phone2() throws IOException {
		return record.valueOf(Address::phone2);
	}

	@Override
	public String city() throws IOException {
		return record.valueOf(Address::city);
	}

	@Override
	public String stateOrProvince() throws IOException {
		return record.valueOf(Address::stateOrProvince);
	}

	@Override
	public String company() throws IOException {
		return record.valueOf(Address::company);
	}

	@Override
	public Country country() throws IOException {
		return new PxCountry(record.of(Address::country)); 
	}

	@Override
	public void defineAddress(String addressLine1, String addressLine2, String email) throws IOException {
		
		record.isRequired(Address::addressLine1, addressLine1);
		
		record.entryOf(Address::addressLine1, addressLine1)
				.entryOf(Address::addressLine2, addressLine2)
				.entryOf(Address::email, email)
				.update();
	}

	@Override
	public void definePhone(String phone1, String phone2) throws IOException {
		
		record.isRequired(Address::phone1, phone1);
		
		record.entryOf(Address::phone1, phone1)
				.entryOf(Address::phone2, phone2)
				.update();
	}

	@Override
	public void defineLocation(String city, String stateOrProvince, Country country)
			throws IOException {

		record.isRequired(Address::city, city);
		record.isRequired(Address::stateOrProvince, stateOrProvince);
		
		record.entryOf(Address::country, country.id())
		      .entryOf(Address::city, city)
		      .entryOf(Address::stateOrProvince, stateOrProvince)
		      .update();
	}

	@Override
	public void defineCompany(String company) throws IOException {
		record.entryOf(Address::company, company)
	      	  .update();
	}

	@Override
	public String email() throws IOException {
		return record.valueOf(Address::email);
	}

	@Override
	public boolean isComplete() throws IOException {
		
		return StringUtils.isNotBlank(email())
				&&
				StringUtils.isNotBlank(addressLine1())
				&&
				StringUtils.isNotBlank(phone1())
				&&
				StringUtils.isNotBlank(city())
				&&
				StringUtils.isNotBlank(stateOrProvince());
	}
}
