package com.supervisor.takes;

import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.xe.XeHub2TokenJson;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.cookies.RsWithCookie;
import org.takes.rq.RqHref;
import org.takes.rs.RsJson;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class TkHub2OpenTransaction extends TkBaseWrap {

	public TkHub2OpenTransaction(Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);
					final User user = module.user();
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("request"));
					
					PaymentRequest payRequest = user.paymentRequests().get(id);
					PaymentMethod method = module.paymentMethods().where(PaymentMethod::tag, "HUB2").first();
					
					// create a PENDING receipt
	            	final Order order = payRequest.order();
	            	final String object = String.format("Paiement par HUB2 du bon de commande N° %s", order.reference());
	            	final PaymentReceipt receipt = order.receipts().add(method, payRequest, object, new HashMap<>());
	            	
					//Code
					URL obj = new URL("https://api.hub2.io/v1/transactions/open");
					HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
					
					//add reuqest header
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/json");
					con.setRequestProperty("H2-Sandbox", "true");
					// con.setRequestProperty("H2-Expected-Response", "failed");
					
					// Send post request
					con.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					final JsonStructure body = Json.createObjectBuilder()
												   .add("merchant_id", method.username())
									               .add("api_key", method.password())
									               .add("customer_id", receipt.remitter().id().toString()) 
									               .add("purchase_ref", receipt.reference())
									               .add(
								            		   "in", 
								            		   Json.createObjectBuilder()
								            		       .add("method", "cc")
								            		       .add("vat", payRequest.order().vatAmount())
								            		       .add("amount", payRequest.amount())
								            		       .add("currency", payRequest.order().currency().code())
									               )
												   .build();
					final String content = body.toString();
					wr.writeBytes(content);					
					wr.flush();
					wr.close();
					
					// Obtenez Input Stream (Le flux de l'entrée) de la Connexion
			        // pour lire des données envoyées par Server.
			        try (
			        		JsonReader jsonReader = Json.createReader(con.getInputStream())
			        ) {
			            JsonObject jsonObject = jsonReader.readObject();
			            boolean success = jsonObject.getBoolean("success");       
			            if(success) {
			            	final String tuid = jsonObject.getString("tuid");
			            	
			            	// create a PENDING receipt
			            	final Map<String, String> metadata = new HashMap<>();
			        		metadata.put("tuid", tuid);	
			            	receipt.updateMetadata(metadata);
			            	
			            	return new RsWithCookie(
		            			new RsJson(new XeHub2TokenJson(tuid)), 
		            			"Hub2Tuid", 
		            			tuid
			            	);
			            } else {
			            	final String message = Json.createObjectBuilder()
							            		       .add("code", "400")
							            		       .add("message", "Hub2 server hasn't been able to open the transaction !")
							            		       .build()
							            		       .toString();
			            	
			            	throw new IllegalArgumentException(message);
			            }
					}
				}
		);
	}
}
