package com.supervisor.billing.impl;

import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentMethods;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxPaymentMethods extends DomainRecordables<PaymentMethod, PaymentMethods> implements PaymentMethods {

	public PxPaymentMethods(RecordSet<PaymentMethod> source) throws IOException {
		super(PxPaymentMethod.class, source);
		
		this.source = source.orderBy(PaymentMethod::id);
	}

	@Override
	protected PaymentMethod domainOf(final Record<PaymentMethod> record) throws IOException {
		
		PaymentMethod method = new PxPaymentMethod(record);
		
		switch (method.tag()) {
			case "MOMO_TEST":
				method = new MobileMoneyMock(method);
				break;
			case "FLOOZ":
				method = new Flooz(method);
				break;
			case "MOMO":
				method = new Momo(method);
				break;
			case "OM":
				method = new Om(method);
				break;
			case "CINETPAY":
				method = new CinetPayCreditCard(method);
				break;
			default:
				break;
		}
		
		return method;
	}
	
}
