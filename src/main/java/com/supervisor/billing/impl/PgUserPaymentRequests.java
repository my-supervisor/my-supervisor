package com.supervisor.billing.impl;

import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.billing.UserPaymentRequests;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;

import java.io.IOException;

public final class PgUserPaymentRequests extends DomainRecordables<PaymentRequest, UserPaymentRequests> implements UserPaymentRequests {

	private final User user;
	
	public PgUserPaymentRequests(final User user) throws IOException {
		this(viewSource(user), user);
	}

	public PgUserPaymentRequests(final RecordSet<PaymentRequest> source, final User user) {
		super(PxPaymentRequest.class, source);
		
		this.user = user;
	}
	
	@Override
	protected UserPaymentRequests domainSetOf(final RecordSet<PaymentRequest> source) throws IOException {
		return new PgUserPaymentRequests(source, user);
	}
	
	private static RecordSet<PaymentRequest> viewSource(final User user) throws IOException{
		
		Table table = new TableImpl(PaymentRequest.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select src.* \r\n" + 
				                        "   from %s as src \r\n" + 
										"	left join %s as target on target.id = src.order_id \r\n" + 
										"   where target.customer_id=%s" + 
										") as %s",
							table.name(),
							new TableImpl(Order.class).name(),
							user.id(),
							table.name()
				);
		
		return user.listOf(PaymentRequest.class, viewScript);
	}
}
