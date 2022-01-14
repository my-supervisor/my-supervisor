package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.AllPaymentReceipts;
import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.sdk.datasource.comparators.Matchers;

import java.io.IOException;

public final class PgAllPaymentReceipts extends DomainRecordables<PaymentReceipt, AllPaymentReceipts> implements AllPaymentReceipts {

	private final User user;
	
	public PgAllPaymentReceipts(final User user) throws IOException {
		this(viewSource(user), user);
	}

	public PgAllPaymentReceipts(final RecordSet<PaymentReceipt> source, final User user) {
		super(PxPaymentReceipt.class, source);
		
		this.user = user;
	}
	
	@Override
	protected AllPaymentReceipts domainSetOf(final RecordSet<PaymentReceipt> source) throws IOException {
		return new PgAllPaymentReceipts(source, user);
	}
	
	private static RecordSet<PaymentReceipt> viewSource(final User user) throws IOException{
		
		Table table = new TableImpl(PaymentReceipt.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select src.* \r\n" + 
				                        "   from %s as src \r\n" + 
										"	left join %s as target on target.id = src.order_id \r\n" + 
										") as %s",
							table.name(),
							new TableImpl(Order.class).name(),
							table.name()
				);
		
		return user.listOf(PaymentReceipt.class, viewScript);
	}

	@Override
	public boolean contains(String token) throws IOException {
		return where(PaymentReceipt::metadata, Matchers.contains(token)).any();
	}

	@Override
	public PaymentReceipt get(String token) throws IOException {
		return where(PaymentReceipt::metadata, Matchers.contains(token)).first();
	}
}
