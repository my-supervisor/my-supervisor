package com.supervisor.billing.impl;

import com.supervisor.domain.Currency;
import com.supervisor.domain.impl.PxCurrency;
import com.supervisor.sdk.datasource.Base;

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
