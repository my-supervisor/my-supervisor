package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;
import java.util.Map;

public interface PaymentReceipts extends DomainSet<PaymentReceipt, PaymentReceipts> {
	PaymentReceipt add(PaymentMethod method, PaymentRequest request, String object, Map<String, String> metadata) throws IOException;
	boolean contains(String token) throws IOException;
	PaymentReceipt get(String token) throws IOException;
}
