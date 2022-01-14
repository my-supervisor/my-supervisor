package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.MobileMoney;
import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.domain.Sequence;
import com.minlessika.membership.domain.impl.PxSequences;

import java.io.IOException;
import java.util.Map;

public final class MobileMoneyMock extends MobileMoneyBase implements MobileMoney {

	public MobileMoneyMock(PaymentMethod method) throws IOException {
		super(method);
	}

	@Override
	protected PaymentReceipt proceed(PaymentRequest paymentRequest, Map<String, String> metadata) throws IOException {
		
		final String fullNumber = metadata.get("FullNumber");
		
		/*if(!(fullNumber.equals("22580203050") || fullNumber.equals("22580203051")))
			throw new IllegalArgumentException("Le compte mobile n'a pas été trouvé !");
		
		double amount = paymentRequest.amount();
		if(amount > 1000000)
			throw new IllegalArgumentException("Erreur interne au système de paiement !");
		
		if(amount > 500000)
			throw new IllegalArgumentException("Les fonds du compte sont insuffisants !");	*/	
		
		Sequence sequence = new PxSequences(record.listOf(Sequence.class)).get("MOMO_TEST");
		String transactionId = sequence.generate();
		
		final Order order = paymentRequest.order();
		final String object = String.format("Paiement par %s (Numéro %s) du bon de commande N° %s", name(), fullNumber, order.reference());
		PaymentReceipt receipt = generatePayment(paymentRequest, object, metadata);
		 
		metadata.put("TransactionId1", transactionId);
		receipt.updateMetadata(metadata); 

		complete(receipt);
		
		return receipt;
	}

	@Override
	public PaymentReceiptState check(PaymentReceipt payment) throws IOException {
		
		/*final PaymentReceiptState state;
		
		final String fullNumber = payment.metadata().get("FullNumber");
		if(fullNumber.equals("22580203051")) {
			state = PaymentReceiptState.PENDING;
		}else {
			state = PaymentReceiptState.CONFIRMED;
		}*/
		
		return PaymentReceiptState.CONFIRMED;
	}
}
