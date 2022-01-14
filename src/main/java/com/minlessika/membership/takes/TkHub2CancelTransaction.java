package com.minlessika.membership.takes;

import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.comparators.Matchers;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.cactoos.list.ListOf;
import org.takes.facets.cookies.RqCookies;
import org.takes.facets.cookies.RsWithCookie;
import org.takes.rs.RsEmpty;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;

public final class TkHub2CancelTransaction extends TkBaseWrap {

	public TkHub2CancelTransaction(Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);
					
					final String tuid = new ListOf<String>(new RqCookies.Base(req).cookie("Hub2Tuid")).get(0);
					PaymentMethod method = module.paymentMethods().where(PaymentMethod::tag, "HUB2").first();
					
					//Code
					URL obj = new URL(String.format("https://api.hub2.io/v1/transactions/%s/cancel", tuid));
					HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
					
					//add reuqest header
					con.setRequestMethod("PUT");
					con.setRequestProperty("Content-Type", "application/json");
					con.setRequestProperty("H2-Sandbox", "true");
					// con.setRequestProperty("H2-Expected-Response", "failed");
					
					// Send post request
					con.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					final JsonStructure body = Json.createObjectBuilder()
												   .add("merchant_id", method.username())
									               .add("api_key", method.password())
												   .build();
					final String bodyStr = body.toString();
					wr.writeBytes(bodyStr);					
					wr.flush();
					wr.close();
					
					// Obtenez Input Stream (Le flux de l'entrée) de la Connexion
			        // pour lire des données envoyées par Server.
			        try (
			        		InputStream is = con.getInputStream();
			        		JsonReader jsonReader = Json.createReader(is)
			        ) {
			            JsonObject jsonObject = jsonReader.readObject();
			            boolean success = jsonObject.getBoolean("success");       
			            if(success) {

			            	// remove pending receipt
			            	final PaymentReceipt receipt =
			            	module.paymentReceipts()
			            		  .where(PaymentReceipt::method, method.id())
			            	      .where(PaymentReceipt::state, PaymentReceiptState.PENDING)
			            	      .where(PaymentReceipt::metadata, Matchers.contains(tuid))
			            	      .first();
			            	
			            	receipt.cancel();
			            	
			            	return new RsWithCookie(
			            				new RsEmpty(), 
			            				"Hub2Tuid", 
			            				"",
			            				"Path=/",
			                        	"Expires=Thu, 01 Jan 1970 00:00:00 GMT"
					               );
			            } else {
			            	final String message = Json.createObjectBuilder()
							            		       .add("code", "400")
							            		       .add("message", "Hub2 server hasn't been able to cancel the transaction !")
							            		       .build()
							            		       .toString();
			            	
			            	throw new IllegalArgumentException(message);
			            }
					}
				}
		);
	}
}
