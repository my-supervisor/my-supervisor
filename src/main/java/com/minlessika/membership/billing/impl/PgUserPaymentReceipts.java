package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.UserPaymentReceipts;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;

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
										"   where target.customer_id=%s" + 
										") as %s",
							table.name(),
							new TableImpl(Order.class).name(),
							user.id(),
							table.name()
				);
		
		return user.listOf(PaymentReceipt.class, viewScript);
	}
}
