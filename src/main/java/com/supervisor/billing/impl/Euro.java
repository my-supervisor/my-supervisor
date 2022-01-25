package com.supervisor.billing.impl;

import com.supervisor.domain.Currency;
import com.supervisor.domain.impl.PxCurrency;
import com.supervisor.sdk.datasource.Base;

import java.io.IOException;

public final class Euro extends CurrencyWrap implements Currency  {

	public Euro(final Base base) throws IOException {
		super(
				new PxCurrency(
						base.select(Currency.class)
						    .where(Currency::tag, "EUR")
						    .first()
				)
		);
	}

}
