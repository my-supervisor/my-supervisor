package com.supervisor.billing;

import com.supervisor.domain.Currency;

import java.io.IOException;

public interface AccountFund {	
	FinancialAccount account() throws IOException;
	Currency currency() throws IOException;
	double amount() throws IOException;
}
