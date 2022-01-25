package com.supervisor.billing;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
	name="billing_financial_account", 
	label="Financial account"
)
public interface FinancialAccount extends Recordable {
	
	@Field(
		label="User",
		rel=Relation.MANY2ONE
	)
	User user() throws IOException;
	
	AccountFunds funds() throws IOException;
	FinancialOrders orders() throws IOException;
}
