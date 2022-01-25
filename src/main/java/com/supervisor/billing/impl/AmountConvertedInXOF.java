package com.supervisor.billing.impl;

import com.supervisor.billing.AmountConvertedInCurrency;
import com.supervisor.domain.Currency;
import com.supervisor.domain.impl.PairEuroXof;

import java.io.IOException;

public final class AmountConvertedInXOF implements AmountConvertedInCurrency {

	private final Currency currency;
	private final double amountToConvert;
	
	public AmountConvertedInXOF(final double amountToConvert, final Currency currency) {		
		this.currency = currency;
		this.amountToConvert = amountToConvert;
	}
	
	@Override
	public double value() throws IOException {
		
		final double amount;
		if(currency.code().equals("XOF")) {
			amount = amountToConvert;
		} else {
			if(currency.code().equals("EUR")) {
				amount = new PairEuroXof(currency.base()).convert(amountToConvert);
			} else {
				throw new IllegalArgumentException(String.format("Express amount in XOF : conversion from %s is not supported !", currency.name()));
			}			
		}
		
		return amount;
	}

}
