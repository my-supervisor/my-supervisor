package com.supervisor.domain.impl;

import com.supervisor.domain.Currency;
import com.supervisor.domain.CurrencyPair;
import com.supervisor.sdk.utils.logging.Logger;
import com.supervisor.sdk.utils.logging.MLogger;
import org.apache.commons.math3.util.Precision;

import java.io.IOException;

public class CurrencyPairBase implements CurrencyPair {

	private static final Logger logger = new MLogger(CurrencyPairBase.class);
	
	private final Currency base;
	private final Currency counterParty;
	private final double rate;
	
	public CurrencyPairBase(final Currency base, final Currency counterParty, final double rate) {
		this.base = base;
		this.counterParty = counterParty;
		
		if(rate <= 0)
			throw new IllegalArgumentException("Vous devez définir un taux de change non nul et positif !");
		
		this.rate = rate;
	}
	
	@Override
	public Currency base() {
		return base;
	}

	@Override
	public Currency counterParty() {
		return counterParty;
	}

	@Override
	public double rate() {
		return rate;
	}

	@Override
	public double convert(double amount) throws IOException {
		return Precision.round(rate * amount, counterParty.precision());
	}

	@Override
	public String toString() {
		try {
			return String.format("%s/%s", base.code(), counterParty.code());
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public CurrencyPair invert() throws IOException {
		return new CurrencyPairBase(counterParty, base, 1/rate);
	}
}
