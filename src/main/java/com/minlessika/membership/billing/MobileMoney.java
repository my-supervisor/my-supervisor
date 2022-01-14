package com.minlessika.membership.billing;

import java.io.IOException;

public interface MobileMoney extends PaymentMethod {	
	
	PaymentReceipt request(String number, PaymentRequest paymentRequest) throws IOException;
	PaymentReceipt request(PaymentRequest paymentRequest) throws IOException;
	void complete(PaymentReceipt payment) throws IOException;
	PaymentReceiptState check(PaymentReceipt payment) throws IOException;
	PaymentReceipt payment(String key) throws IOException;
	
}