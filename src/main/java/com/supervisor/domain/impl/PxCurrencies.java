package com.supervisor.domain.impl;

import com.supervisor.domain.Currencies;
import com.supervisor.domain.Currency;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxCurrencies extends DomainRecordables<Currency, Currencies> implements Currencies {

	public PxCurrencies(final RecordSet<Currency> source) {
		super(PxCurrency.class, source);
	}

	@Override
	public Currency add(String code, String name, String symbol) throws IOException {
		
		source.isRequired(Currency::code, code);
		source.isRequired(Currency::name, name);	
		source.isRequired(Currency::symbol, symbol);
		
		Record<Currency> record = source.entryOf(Currency::code, code)
								        .entryOf(Currency::name, name)
								        .entryOf(Currency::symbol, symbol)
								        .entryOf(Currency::after, true)
								        .entryOf(Currency::precision, 0)
								        .add();
		
		return domainOf(record);
	}
	
}
