package com.supervisor.billing.impl;

import com.supervisor.billing.BillingAddress;
import com.supervisor.domain.Address;
import com.supervisor.domain.Country;
import com.supervisor.domain.impl.PxAddress;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxBillingAddress extends DomainRecordable<BillingAddress> implements BillingAddress {

	private final Address origin;
	
	public PxBillingAddress(Record<BillingAddress> record) throws IOException {
		super(record);
		
		this.origin = new PxAddress(
							record.listOf(Address.class)
							      .get(record.id())
					  );
	}

	public PxBillingAddress(Address origin) throws IOException {
		this(origin.listOf(BillingAddress.class).get(origin.id()));
	}

	@Override
	public String addressLine1() throws IOException {
		return origin.addressLine1();
	}

	@Override
	public String addressLine2() throws IOException {
		return origin.addressLine2();
	}

	@Override
	public String email() throws IOException {
		return origin.email();
	}

	@Override
	public String phone1() throws IOException {
		return origin.phone1();
	}

	@Override
	public String phone2() throws IOException {
		return origin.phone2();
	}

	@Override
	public String city() throws IOException {
		return origin.city();
	}

	@Override
	public String stateOrProvince() throws IOException {
		return origin.stateOrProvince();
	}

	@Override
	public String company() throws IOException {
		return origin.company();
	}

	@Override
	public Country country() throws IOException {
		return origin.country();
	}

	@Override
	public void defineCompany(String company) throws IOException {
		origin.defineCompany(company);
	}

	@Override
	public void defineAddress(String addressLine1, String addressLine2, String email) throws IOException {
		origin.defineAddress(addressLine1, addressLine2, email);
	}

	@Override
	public void definePhone(String phone1, String phone2) throws IOException {
		origin.definePhone(phone1, phone2);
	}

	@Override
	public void defineLocation(String city, String stateOrProvince, Country country) throws IOException {
		origin.defineLocation(city, stateOrProvince, country); 
	}

	@Override
	public boolean submittedToVat() throws IOException {
		return record.valueOf(BillingAddress::submittedToVat);
	}

	@Override
	public void changeVATStatus(boolean submitted) throws IOException {
		record.entryOf(BillingAddress::submittedToVat, submitted)
		      .update();
	}

	@Override
	public boolean isComplete() throws IOException {
		return origin.isComplete();
	}
	
}
