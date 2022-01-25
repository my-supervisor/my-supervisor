package com.supervisor.billing;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface PaymentMethodReceipts extends DomainSet<PaymentReceipt, PaymentMethodReceipts> {
	boolean contains(String token) throws IOException;
	boolean hasAnyBelongTo(PaymentRequest paymentRequest) throws IOException;
	PaymentReceipt get(String token) throws IOException;
	
	PaymentMethodReceipts of(PaymentRequest paymentRequest) throws IOException;
}
