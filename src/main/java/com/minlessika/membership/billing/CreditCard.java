package com.minlessika.membership.billing;

import java.io.IOException;

public interface CreditCard extends PaymentMethod {		
	PaymentReceipt request(PaymentRequest paymentRequest) throws IOException;	
}