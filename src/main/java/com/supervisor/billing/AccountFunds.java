package com.supervisor.billing;

import java.io.IOException;
import java.util.List;

public interface AccountFunds {
	FinancialAccount account() throws IOException;
	List<AccountFund> items() throws IOException;
}
