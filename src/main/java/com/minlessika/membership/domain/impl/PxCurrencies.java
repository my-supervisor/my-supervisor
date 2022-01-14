package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Currencies;
import com.minlessika.membership.domain.Currency;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

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
