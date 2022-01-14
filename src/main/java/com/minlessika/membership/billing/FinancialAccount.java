package com.minlessika.membership.billing;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
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
