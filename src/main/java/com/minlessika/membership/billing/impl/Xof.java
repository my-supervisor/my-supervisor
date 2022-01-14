package com.minlessika.membership.billing.impl;

import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.impl.PxCurrency;
import com.minlessika.sdk.datasource.Base;

import java.io.IOException;

public final class Xof extends CurrencyWrap implements Currency  {

	public Xof(final Base base) throws IOException {
		super(
				new PxCurrency(
						base.select(Currency.class)
						    .where(Currency::tag, "XOF")
						    .first()
				)
		);
	}

}
