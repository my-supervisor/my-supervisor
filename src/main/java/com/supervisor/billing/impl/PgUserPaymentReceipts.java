package com.supervisor.billing.impl;

import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.UserPaymentReceipts;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;

import java.io.IOException;

public final class PgUserPaymentReceipts extends DomainRecordables<PaymentReceipt, UserPaymentReceipts> implements UserPaymentReceipts {

	private final User user;
	
	public PgUserPaymentReceipts(final User user) throws IOException {
		this(viewSource(user), user);
	}

	public PgUserPaymentReceipts(final RecordSet<PaymentReceipt> source, final User user) {
		super(PxPaymentReceipt.class, source);
		
		this.user = user;
	}
	
	@Override
	protected UserPaymentReceipts domainSetOf(final RecordSet<PaymentReceipt> source) throws IOException {
		return new PgUserPaymentReceipts(source, user);
	}
	
	private static RecordSet<PaymentReceipt> viewSource(final User user) throws IOException{
		
		Table table = new TableImpl(PaymentReceipt.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select src.* \r\n" + 
				                        "   from %s as src \r\n" + 
										"	left join %s as target on target.id = src.order_id \r\n" + 
										"   where target.customer_id='%s'::uuid" +
										") as %s",
							table.name(),
							new TableImpl(Order.class).name(),
							user.id(),
							table.name()
				);
		
		return user.listOf(PaymentReceipt.class, viewScript);
	}
}
