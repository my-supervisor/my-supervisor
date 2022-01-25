package com.supervisor.billing.impl;

import com.supervisor.billing.MobileMoney;
import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.billing.PaymentRequest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public final class Flooz extends MobileMoneyBase implements MobileMoney {

	public Flooz(PaymentMethod method) throws IOException {
		super(method);
	}

	@Override
	public PaymentReceiptState check(PaymentReceipt payment) throws IOException {
		
		//Code
		URL obj = new URL("https://floozapi.webtechci.com/interface/api.php");
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		final String transactionId1 = payment.metadata().get("TransactionId1");
		wr.writeBytes(String.format("action=getStatus&user=%s&password=%s&id=%s", username(), password(), transactionId1));
		
		wr.flush();
		wr.close();
		
		// Obtenez Input Stream (Le flux de l'entrée) de la Connexion
        // pour lire des données envoyées par Server.
        InputStream is = con.getInputStream();

        JsonReader jsonReader = null;        
        try {
        	
        	final PaymentReceiptState state;
        	
        	jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            int status = Integer.parseInt(jsonObject.getString("status"));  
            switch (status) {
				case 1:
				case 2:
					state = PaymentReceiptState.PENDING;
					break;
				case 3:
					state = PaymentReceiptState.CONFIRMED;
					break;
				default:
					state = PaymentReceiptState.CANCELLED;
					break;
			}
            
            return state;
		} finally {
			if(jsonReader != null)
				jsonReader.close();
		}
	}

	@Override
	protected PaymentReceipt proceed(PaymentRequest paymentRequest, Map<String, String> metadata) throws IOException {
		
		final String fullNumber = metadata.get("FullNumber");
		final Order order = paymentRequest.order();
		final String object = String.format("Paiement par %s (Numéro %s) du bon de commande N° %s", name(), fullNumber, order.reference());

		// create a PENDING receipt						
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
		URL obj = new URL("https://floozapi.webtechci.com/interface/api.php");
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(String.format("action=request&user=%s&password=%s&msisdn=%s&amount=%s&reference=%s", username(), password(), fullNumber, paymentRequest.amount(), receipt.reference()));
		
		wr.flush();
		wr.close();
		
		// Obtenez Input Stream (Le flux de l'entrée) de la Connexion
        // pour lire des données envoyées par Server.
        InputStream is = con.getInputStream();

        JsonReader jsonReader = null;        
        
        try {
        	jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            int success = jsonObject.getInt("success");       
            if(success == 1) {
            	String transactionId = String.format("%s", jsonObject.getInt("id"));             	           	
        		metadata.put("TransactionId1", transactionId);
        		receipt.updateMetadata(metadata); 
            } else {
            	final String message = jsonObject.getString("message");
            	throw new IllegalArgumentException(message);
            }
		} finally {
			if(jsonReader != null)
				jsonReader.close();
		}
        
        return receipt;
	}
}
