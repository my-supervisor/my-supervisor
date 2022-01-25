package com.supervisor.billing.impl;

import com.supervisor.billing.MobileMoney;
import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.UserOf;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class Om extends MobileMoneyBase implements MobileMoney {

	public Om(PaymentMethod method) throws IOException {
		super(method);
	}

	@Override
	public PaymentReceiptState check(PaymentReceipt payment) throws IOException {
		
		//Code
		URL obj = new URL("https://ompay.orange.ci/e-commerce/checkStatus.php");
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		final String token = payment.metadata().get("token");
		wr.writeBytes(String.format("merchantid=%s&token=%s", username(), token));
		
		wr.flush();
		wr.close();
		
		String inputLine;
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
		    response.append(inputLine);
		}
		
		in.close();
		String plainText = response.toString();
		
		final Map<String, String> fields = new HashMap<>();
		final String[] params = plainText.split(";");
		for (String param : params) {
			final String[] couple = param.split("=");
			final String key = couple[0];
			final String value;
			if(couple.length == 2)
				value = couple[1];
			else
				value = "";
			
			fields.put(key, value);
		}	
		
		final PaymentReceiptState state;

        int status = Integer.parseInt(fields.get("status"));  
        if(status == 0) {
        	state = PaymentReceiptState.CONFIRMED;
        } else {
        	state = PaymentReceiptState.CANCELLED;
        }
        
        return state;
	}

	@Override
	protected PaymentReceipt proceed(PaymentRequest paymentRequest, Map<String, String> metadata) throws IOException {		

		final User user = new UserOf(this);
		final Order order = paymentRequest.order();
		
		// create a PENDING receipt		
		final String object = String.format("Paiement par Orange Money du bon de commande N° %s", order.reference());
		final PaymentReceipt receipt;
		if(pendingPayments().hasAnyBelongTo(paymentRequest)) {
			 final PaymentReceipt pendingReceipt = pendingPayments().of(paymentRequest).last();
			 if(check(pendingReceipt) == PaymentReceiptState.PENDING) {
				 receipt = pendingReceipt;
			 } else {
				 pendingReceipt.cancel();
				 receipt = generatePayment(paymentRequest, object, metadata);
			 }
		} else {
			receipt = generatePayment(paymentRequest, object, metadata);
		}
		
		//Code
		URL obj = new URL("https://ompay.orange.ci/e-commerce/init.php");
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		final int amount = (int)Math.round(receipt.amount());
		final String clearWord = String.format("%s::%s", user.id(), receipt.id());
		final String sessionId = new String(
						            Base64.getEncoder().encode(clearWord.getBytes(StandardCharsets.UTF_8))
						         );
		
		final String body = String.format("merchantid=%s&amount=%s&sessionid=%s&description=%s&purchaseref=%s", username(), amount, sessionId, receipt.object(), receipt.reference());
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Accept-Language", user.preferredLanguage().isoCode());
		
		// Send post request
		con.setDoOutput(true);
		final DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(body);
		
		wr.flush();
		wr.close();
		
		String inputLine;
		try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
			    response.append(inputLine);
			}
			
			String xml = response.toString();
			if(xml.toLowerCase().contains("access denied")) {
				throw new IOException(xml);
			} else {
				final String token = xml;
				// create a PENDING receipt            	
        		metadata.put("token", token);	
            	receipt.updateMetadata(metadata);         	
			}
		}
		
		return receipt;
	}

}
