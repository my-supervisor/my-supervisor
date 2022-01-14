package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;
import java.util.Map;

public interface PaymentRequests extends DomainSet<PaymentRequest, PaymentRequests> {
	PaymentRequest add(String object, double amount, String notes, Map<String, String> metadata) throws IOException;
}
