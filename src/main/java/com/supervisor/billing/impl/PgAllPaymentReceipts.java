package com.supervisor.billing.impl;

import com.supervisor.billing.AllPaymentReceipts;
import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.datasource.comparators.Matchers;

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
