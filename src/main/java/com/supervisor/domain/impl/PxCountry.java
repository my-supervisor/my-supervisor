package com.supervisor.domain.impl;

import com.supervisor.domain.Country;
import com.supervisor.domain.Currency;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxCountry extends DomainRecordable<Country> implements Country {

	public PxCountry(Record<Country> record) throws IOException {
		super(record);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(Country::code);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Country::name);
	}

	@Override
	public Currency currency() throws IOException {
		Record<Currency> rec = record.of(Country::currency);
		return new PxCurrency(rec);
	}

	@Override
	public int phoneCode() throws IOException {
		return record.valueOf(Country::phoneCode);
	}

}
