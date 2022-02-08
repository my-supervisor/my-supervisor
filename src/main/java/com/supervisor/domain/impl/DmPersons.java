package com.supervisor.domain.impl;

import com.supervisor.billing.BillingAddress;
import com.supervisor.billing.BillingAddresses;
import com.supervisor.billing.impl.PxBillingAddresses;
import com.supervisor.domain.Address;
import com.supervisor.domain.Addresses;
import com.supervisor.domain.Person;
import com.supervisor.domain.Persons;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;
import java.util.UUID;

public final class DmPersons extends DomainRecordables<Person, Persons> implements Persons {

	public DmPersons(final RecordSet<Person> source) {
		super(DmPerson.class, source);
	}

	@Override
	public Person add(String name, String email) throws IOException {

		source.isRequired(Person::name, name);		
		
		final Addresses addresses = new PxAddresses(source.of(Address.class));
		if(addresses.where(Address::email, email).any())
			throw new IllegalArgumentException("This email address has already been used !");
		
		final User user = new UserOf(this);
		Address address = addresses.add(
								email, 
								user.address().country()
						  );
		
		final BillingAddresses billingAddresses = new PxBillingAddresses(source.of(BillingAddress.class));
		BillingAddress billingAddress = billingAddresses.add(address);
		
		final String defaultPhoto = "data:image/jpeg;base64,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";
		
		Record<Person> record = source.entryOf(Person::name, name)
						      		  .entryOf(Person::isCompany, false)
						      		  .entryOf(Person::photo, defaultPhoto)
						      		  .entryOf(Person::timeZoneId, user.timeZoneId())
						      		  .entryOf(Person::preferredLanguage, UUID.fromString("602667bc-8014-4a02-be12-164a8e1c29cb")) // English
						      		  .entryOf(Person::address, address.id())
						      		  .entryOf(Person::billingAddress, billingAddress.id())
						      		  .entryOf(Person::preferredCurrency, UUID.fromString("75932d0b-f391-4def-a2e0-854e45376f07")) // Euro
						      		  .add();
		
		return domainOf(record);
	}
}
