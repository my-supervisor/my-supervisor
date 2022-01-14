package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Currency;

import java.io.IOException;

public interface AccountFund {	
	FinancialAccount account() throws IOException;
	Currency currency() throws IOException;
	double amount() throws IOException;
}
