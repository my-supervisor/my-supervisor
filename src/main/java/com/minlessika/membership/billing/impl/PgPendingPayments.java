package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.billing.PendingPayments;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;

import java.io.IOException;

public final class PgPendingPayments extends DomainRecordables<PaymentRequest, PendingPayments> implements PendingPayments {

	private final User user;
	
	public PgPendingPayments(final User user) throws IOException {
		this(viewSource(user), user);
	}

	public PgPendingPayments(final RecordSet<PaymentRequest> source, final User user) {
		super(PxPaymentRequest.class, source);
		
		this.user = user;
	}
	
	@Override
	protected PendingPayments domainSetOf(final RecordSet<PaymentRequest> source) throws IOException {
		return new PgPendingPayments(source, user);
	}
	
	private static RecordSet<PaymentRequest> viewSource(final User user) throws IOException{
		
		Table table = new TableImpl(PaymentRequest.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select src.* \r\n" + 
				                        "   from %s as src \r\n" + 
										"	left join %s as target on target.id = src.order_id \r\n" + 
										"   where target.customer_id=%s and src.status = 'PENDING'" + 
										") as %s",
							table.name(),
							new TableImpl(Order.class).name(),
							user.id(),
							table.name()
				);
		
		return user.listOf(PaymentRequest.class, viewScript);
	}
}
