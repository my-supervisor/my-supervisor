package com.supervisor.billing;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface AllPaymentReceipts extends DomainSet<PaymentReceipt, AllPaymentReceipts> {
	boolean contains(String token) throws IOException;
	PaymentReceipt get(String token) throws IOException;
}
