package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.MobileMoney;
import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.billing.PaymentReceipts;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.UserOf;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class MobileMoneyBase extends PxPaymentMethod implements MobileMoney {
	
	public MobileMoneyBase(final PaymentMethod method) throws IOException {
		super(method.listOf(PaymentMethod.class).get(method.id()));	
	}
	
	protected abstract PaymentReceipt proceed(PaymentRequest request, Map<String, String> metadata) throws IOException;
	
	protected final PaymentReceipt generatePayment(PaymentRequest paymentRequest, String object, Map<String, String> metadata) throws IOException {
		
		final Order order = paymentRequest.order();		
		return order.receipts().add(this, paymentRequest, object, metadata);
	}
	
	@Override
	public PaymentReceipt request(String number, PaymentRequest paymentRequest) throws IOException {
		
		// vérifier que l'utilisateur vient de la côte d'ivoire
		User user = new UserOf(this);
		
		if(!user.address().country().code().equals("CI"))
			throw new IllegalArgumentException("Ce moyen de paiement n'est disponible que pour les abonnés de Côte d'Ivoire !");
				
		if(!paymentRequest.order().currency().code().equals("XOF"))
			throw new IllegalArgumentException("Ce moyen de paiement n'accepte que les montants en devise F CFA !");
		
		record.mustCheckThisCondition(
				StringUtils.isNotBlank(number), 
				"Vous devez renseigner un numéro !"
		);
		
		record.mustCheckThisCondition(
				number.length() == 10, 
				"Vous devez saisir un numéro à 10 chiffres !"
		);
		
		record.mustCheckThisCondition(
				paymentRequest.amount() > 0, 
				"Vous devez saisir un montant supérieur à zéro !"
		);
		
		PaymentReceipts pendingPayments = paymentRequest.order()
				                               .receipts()
										       .where(PaymentReceipt::method, id())
										       .where(PaymentReceipt::state, PaymentReceiptState.PENDING);
		
		if(pendingPayments.any()) {
		   // vérifier que la transaction est toujours en attente
			PaymentReceipt receipt = pendingPayments.first();
			
			PaymentReceiptState state = check(receipt);
			if(state == PaymentReceiptState.PENDING)
				throw new IllegalArgumentException("Une transaction est en attente de confirmation !");
			
			if(state == PaymentReceiptState.CONFIRMED)
				throw new IllegalArgumentException("La dernière transaction a été confirmée !");
			
			if(state == PaymentReceiptState.CANCELLED)
				receipt.cancel();			
		}
		
		String fullNumber = String.format("%s%s", user.address().country().phoneCode(), number);
		final Map<String, String> metadata = new HashMap<>();
		metadata.put("FullNumber", fullNumber);

		return proceed(paymentRequest, metadata);
	}
	
	@Override
	public PaymentReceipt request(PaymentRequest paymentRequest) throws IOException {
		
		// vérifier que l'utilisateur vient de la côte d'ivoire
		final User user = new UserOf(this);
		
		if(!user.address().country().code().equals("CI"))
			throw new IllegalArgumentException("Ce moyen de paiement n'est disponible que pour les abonnés de Côte d'Ivoire !");
		
		if(!paymentRequest.order().currency().code().equals("XOF"))
			throw new IllegalArgumentException("Ce moyen de paiement n'accepte que les montants en devise F CFA !");
		
		
		record.mustCheckThisCondition(
				paymentRequest.amount() > 0, 
				"Vous devez saisir un montant supérieur à zéro !"
		);
		
		PaymentReceipts pendingPayments = paymentRequest.order()
				                               .receipts()
										       .where(PaymentReceipt::method, id())
										       .where(PaymentReceipt::state, PaymentReceiptState.PENDING);
		
		if(pendingPayments.any()) {
		   // vérifier que la transaction est toujours en attente
			PaymentReceipt receipt = pendingPayments.first();
			
			PaymentReceiptState state = check(receipt);
			if(state == PaymentReceiptState.PENDING)
				throw new IllegalArgumentException("Une transaction est en attente de confirmation !");
			
			if(state == PaymentReceiptState.CONFIRMED)
				throw new IllegalArgumentException("La dernière transaction a été confirmée !");
			
			if(state == PaymentReceiptState.CANCELLED)
				receipt.cancel();			
		}
		
		return proceed(paymentRequest, new HashMap<>());
	}
}
