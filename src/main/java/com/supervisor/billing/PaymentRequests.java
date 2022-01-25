package com.supervisor.billing;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;
import java.util.Map;

public interface PaymentRequests extends DomainSet<PaymentRequest, PaymentRequests> {
	PaymentRequest add(String object, double amount, String notes, Map<String, String> metadata) throws IOException;
}
