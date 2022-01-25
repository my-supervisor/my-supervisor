package com.supervisor.billing.impl;

import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentMethodReceipts;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.datasource.comparators.Matchers;

import java.io.IOException;

public final class PgPaymentMethodReceipts extends DomainRecordables<PaymentReceipt, PaymentMethodReceipts> implements PaymentMethodReceipts {

	private final PaymentMethod method;
	
	public PgPaymentMethodReceipts(final PaymentMethod method) throws IOException {
		this(viewSource(method), method);
	}

	public PgPaymentMethodReceipts(final RecordSet<PaymentReceipt> source, final PaymentMethod method) throws IOException {
		super(PxPaymentReceipt.class, source);
		
		this.method = method;
		this.source = source.where(PaymentReceipt::method, method.id());
	}
	
	@Override
	protected PaymentMethodReceipts domainSetOf(final RecordSet<PaymentReceipt> source) throws IOException {
		return new PgPaymentMethodReceipts(source, method);
	}
	
	private static RecordSet<PaymentReceipt> viewSource(final PaymentMethod method) throws IOException {
		
		Table table = new TableImpl(PaymentReceipt.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select src.* \r\n" + 
				                        "   from %s as src \r\n" + 
										") as %s",
										table.name(),
										table.name()
							);
		
		return method.listOf(PaymentReceipt.class, viewScript);
	}
	
	@Override
	public boolean contains(String token) throws IOException {
		return where(PaymentReceipt::metadata, Matchers.contains(token)).any();
	}

	@Override
	public PaymentReceipt get(String token) throws IOException {
		return where(PaymentReceipt::metadata, Matchers.contains(token)).first();
	}

	@Override
	public boolean hasAnyBelongTo(PaymentRequest paymentRequest) throws IOException {
		return of(paymentRequest).any();
	}

	@Override
	public PaymentMethodReceipts of(PaymentRequest paymentRequest) throws IOException {
		return where(PaymentReceipt::request, paymentRequest.id());
	}
}
