package com.supervisor.domain.impl;

import com.supervisor.billing.BillingAddress;
import com.supervisor.billing.impl.PxBillingAddress;
import com.supervisor.domain.Address;
import com.supervisor.domain.Currency;
import com.supervisor.domain.Language;
import com.supervisor.domain.Person;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

public final class DmPerson extends DomainRecordable<Person> implements Person {
	
	public DmPerson(final Record<Person> source) throws IOException {
		super(source);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Person::name); 
	}

	@Override
	public boolean isCompany() throws IOException {
		return record.valueOf(Person::isCompany);
	}

	@Override
	public void update(String name, String photo, boolean isCompany) throws IOException {
		
		record.isRequired(Person::name, name);
		record.isRequired(Person::photo, photo);
		
		record.entryOf(Person::name, name)
			  .entryOf(Person::photo, photo)
			  .entryOf(Person::isCompany, isCompany)
			  .update();
	}

	@Override
	public String photo() throws IOException {
		return record.valueOf(Person::photo);
	}

	@Override
	public TimeZone timeZone() throws IOException {
		return TimeZone.getTimeZone(timeZoneId());
	}

	@Override
	public void changeTimeZone(TimeZone timeZone) throws IOException {
		
		record.entryOf(Person::timeZoneId, timeZone.getID())
		      .update();
	}

	@Override
	public Language preferredLanguage() throws IOException {
		Record<Language> rec = record.of(Person::preferredLanguage);
		return new PxLanguage(rec);
	}

	@Override
	public void changePreferredLanguage(Language language) throws IOException {
		
		record.entryOf(Person::preferredLanguage, language.id())
		      .update();
	}

	@Override
	public String timeZoneId() throws IOException {
		return record.valueOf(Person::timeZoneId);
	}

	@Override
	public Currency preferredCurrency() throws IOException {
		return new PxCurrency(
			record.of(Person::preferredCurrency)
		);
	}

	@Override
	public Address address() throws IOException {
		return new PxAddress(record.of(Person::address));
	}

	@Override
	public Locale locale() throws IOException {
		return new Locale(preferredLanguage().isoCode(), address().country().code());
	}

	@Override
	public void changePreferredCurrency(Currency currency) throws IOException {
		
		record.entryOf(Person::preferredCurrency, currency.id())
	          .update();
	}

	@Override
	public BillingAddress billingAddress() throws IOException {
		return new PxBillingAddress(record.of(Person::billingAddress));
	}
}
