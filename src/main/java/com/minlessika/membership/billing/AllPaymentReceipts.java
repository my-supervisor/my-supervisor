package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface AllPaymentReceipts extends DomainSet<PaymentReceipt, AllPaymentReceipts> {
	boolean contains(String token) throws IOException;
	PaymentReceipt get(String token) throws IOException;
}
