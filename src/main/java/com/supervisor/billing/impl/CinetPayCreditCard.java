package com.supervisor.billing.impl;

import com.supervisor.billing.CreditCard;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.UserOf;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class CinetPayCreditCard extends PxPaymentMethod implements CreditCard {

	private static boolean isValidJson(String jsonStr) {
        boolean isValid = true;
        try(JsonReader jsonReader = Json.createReader(new StringReader(jsonStr))) {
        	jsonReader.readObject();
        } catch (Exception e) {
        	isValid = false;
        }

        return isValid;
    }
	
	public CinetPayCreditCard(final PaymentMethod method) throws IOException {
		super(method.listOf(PaymentMethod.class).get(method.id()));
	}

	@Override
	public PaymentReceipt request(PaymentRequest paymentRequest) throws IOException {
		
		final User user = new UserOf(this);
		
		final PaymentReceipt receipt;
		if(pendingPayments().hasAnyBelongTo(paymentRequest)) {
			 final PaymentReceipt pendingReceipt = pendingPayments().of(paymentRequest).last();
			 if(check(pendingReceipt) == PaymentReceiptState.PENDING) {
				 receipt = pendingReceipt;
			 } else {
				 pendingReceipt.cancel();
				 receipt = paymentRequest.order().receipts().add(this, paymentRequest, paymentRequest.object(), new HashMap<>());
			 }
		} else {
			receipt = paymentRequest.order().receipts().add(this, paymentRequest, paymentRequest.object(), new HashMap<>());
		}
		
		//Code
		URL obj = new URL("https://api.cinetpay.com/v1/?method=getSignatureByPost");
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
							
		// Send post request
		con.setDoOutput(true);
		try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())){
			final String content = String.format(
					"cpm_site_id=%s&apikey=%s&cpm_trans_id=%s&cpm_trans_date=%s&cpm_amount=%s&cpm_currency=%s&cpm_payment_config=%s&cpm_page_action=%s&cpm_version=%s&cpm_language=%s&cpm_designation=%s&cpm_return_mode=%s", 
					username(),
					password(),
					receipt.reference(),
					receipt.creationDate().toString(),
					(int)new AmountConvertedInXOF(receipt.amount(), receipt.currency()).value(),
					"CFA",
					"SINGLE",
					"PAYMENT",
					"V1",
					user.preferredLanguage().isoCode(),
					receipt.object(),
					"POST"
		   );			
			
			wr.writeBytes(content);
			wr.flush();
		} catch(Exception e) {
			throw new IllegalArgumentException(e.getLocalizedMessage());
		}
		
		StringBuilder response = new StringBuilder();
		try(InputStreamReader in = new InputStreamReader(con.getInputStream())) {
			try(BufferedReader buff = new BufferedReader(in)){
				String inputLine;
				while ((inputLine = buff.readLine()) != null) {
				    response.append(inputLine);
				}
				
				String data = response.toString();
				
				if(isValidJson(data)) {
					// An error has occurred
			        try (
			        		JsonReader jsonReader = Json.createReader(new StringReader(data))
			        ) {
			            JsonObject jsonObject = jsonReader.readObject().getJsonObject("status");
			            String code = jsonObject.getString("code");
			            switch (code) {
						case "609":
							throw new IllegalArgumentException("Authentification to API not authorized ! Check yours credentials with CinetPay.");
						case "613":
							throw new IllegalArgumentException("SITE ID not found ! Check yours credentials with CinetPay.");
						default:
							throw new IllegalArgumentException(String.format("An error occured : %s (%s).", jsonObject.getString("message"), code));
						}
					}
				} else {
					// redirect to CINETPAY page
					final String token = data.replaceAll("\"", "");
					
					final Map<String, String> metadata = receipt.metadata();
	            	metadata.put("token", token);
	            	receipt.updateMetadata(metadata); 
	            	return receipt;    
				}							
			}
		} catch(Exception e) {
			throw new IllegalArgumentException(e.getLocalizedMessage());
		}	
	}

	@Override
	public PaymentReceiptState check(PaymentReceipt payment) throws IOException {
		
		final String apiKey = password();
		final String siteID = username();
		final String transactionId = payment.reference();
		
		//Code
		URL obj = new URL("https://api.cinetpay.com/v1/?method=checkPayStatus");
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		// Send post request
		con.setDoOutput(true);
		try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())){
			final String content = String.format(
					"cpm_site_id=%s&apikey=%s&cpm_trans_id=%s", 
					siteID,
					apiKey,
					transactionId
		   );			
			
			wr.writeBytes(content);
			wr.flush();
		} catch(Exception e) {					
			throw new IllegalArgumentException(e.getLocalizedMessage());
		}
		
		final PaymentReceiptState state;
		try(InputStreamReader in = new InputStreamReader(con.getInputStream())) {
			try(JsonReader jsonReader = Json.createReader(in)){
				final JsonObject jsonObject = jsonReader.readObject().getJsonObject("transaction");
				final String code = jsonObject.getString("cpm_result");							
				switch (code) {
					case "00":
						state = PaymentReceiptState.CONFIRMED;								
						break;
					case "607":
					case "623":
					case "624":
						state = PaymentReceiptState.PENDING;
						break;
					default:
						state = PaymentReceiptState.CANCELLED;
				}				
			}
		} catch(Exception e) {
			throw new IllegalArgumentException(e.getLocalizedMessage());
		}					
		
		return state;
	}
}
