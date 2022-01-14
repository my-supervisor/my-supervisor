package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Currency;
import com.minlessika.sdk.datasource.Base;

import java.io.IOException;

public final class PairEuroXof extends CurrencyPairBase {

	public PairEuroXof(final Base base) throws IOException {
		super(currencyOf(base, "EUR"), currencyOf(base, "XOF"), 655.957);
	}

	private static Currency currencyOf(Base base, String code) throws IOException {
		return new DmMembership(base, new UserOf(base)).currencies().where(Currency::code, code).first();
	}
}
