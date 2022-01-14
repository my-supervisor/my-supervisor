package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.CurrencyPair;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public final class PxCurrency extends DomainRecordable<Currency> implements Currency {

	public PxCurrency(final Record<Currency> source) throws IOException {
		super(source);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(Currency::code);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Currency::name);
	}

	@Override
	public String symbol() throws IOException {
		return record.valueOf(Currency::symbol);
	}

	@Override
	public boolean after() throws IOException {
		return record.valueOf(Currency::after);
	}

	@Override
	public int precision() throws IOException {
		return record.valueOf(Currency::precision);
	}

	@Override
	public void update(String code, String name, String symbol, boolean after, int precision) throws IOException {
		
		record.isRequired(Currency::code, code);
		record.isRequired(Currency::name, name);	
		record.isRequired(Currency::symbol, symbol);
		
		record.mustCheckThisCondition(precision >= 0, "La précision doit être un nombre positif non nul !");
		
		record.entryOf(Currency::code, code)
		      .entryOf(Currency::name, name)
		      .entryOf(Currency::symbol, symbol)
		      .entryOf(Currency::after, after)
		      .entryOf(Currency::precision, precision)
		      .update();
	}

	@Override
	public String toString(double amount) throws IOException {
		
		NumberFormat nf = NumberFormat.getInstance(new UserOf(this).locale());
		nf.setMaximumFractionDigits(precision());
		
		if(after())
			return String.format("%s %s", nf.format(amount), symbol());
		else
			return String.format("%s %s", symbol(), nf.format(amount));
	}

	@Override
	public double convert(double amount, Currency currency) throws IOException {
		
		if(currency.equals(this) || code().equals(currency.code()))
			return amount;
		
		final List<String> validCodes = Arrays.asList("XOF", "EUR");
		if(!(validCodes.contains(currency.code()) && validCodes.contains(code())))
			throw new IllegalArgumentException("Pair currency rate conversion not supported ! Must be XOF or EUR.");
			
		final CurrencyPair pairEuroXof = new PairEuroXof(base());
		
		if(code().equals("XOF")) {
			return amount;
		} else {
			return pairEuroXof.invert().convert(amount);
		}
	}
	
}
