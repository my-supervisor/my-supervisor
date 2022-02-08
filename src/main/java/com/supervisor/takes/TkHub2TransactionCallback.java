package com.supervisor.takes;

import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.domain.impl.DmUser;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsEmpty;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;

public final class TkHub2TransactionCallback extends TkBaseWrap {

	public TkHub2TransactionCallback(final Base base) {
		super(
				base,
				req -> {
					
					RecordSet<User> source = base.select(User.class);
					User user = new DmUser(source.get(User.ADMIN_ID)); // prendre l'utilisateur Minlessika
					
					final Membership module = new DmMembership(base, user);
					
					final Smart href = new Smart(new RqGreedy(req));
					final String tuid = href.single("tuid");
					final String action = href.single("action");
					final String s = href.single("s");
					
					if(action.equals("get_status") && s.equals("minlessika")) {
						
						//Code
						URL obj = new URL(String.format("https://api.hub2.io/v1/transactions/%s/status/in", tuid));
						HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
						
						//add reuqest header
						con.setRequestMethod("GET");
						con.setRequestProperty("Content-Type", "application/json");
						con.setRequestProperty("H2-Sandbox", "true");
						// con.setRequestProperty("H2-Expected-Response", "failed");
						
						// Obtenez Input Stream (Le flux de l'entrée) de la Connexion
				        // pour lire des données envoyées par Server.
				        try (
				        		InputStream is = con.getInputStream();
				        		JsonReader jsonReader = Json.createReader(is)
				        ) {
				            JsonObject jsonObject = jsonReader.readObject();
				            boolean success = jsonObject.getBoolean("success");    				            
				            if(success) {
				            				  
				            	PaymentMethod method = module.paymentMethods().where(PaymentMethod::tag, "HUB2").first();
				            	
				            	// get pending receipt
				            	final PaymentReceipt receipt =
				            	module.allPaymentReceipts()
				            	      .where(PaymentReceipt::method, method.id())
				            	      .where(PaymentReceipt::state, PaymentReceiptState.PENDING)
				            	      .where(PaymentReceipt::metadata, Matchers.contains(tuid))
				            	      .first();
				            	
				            	final String code = jsonObject.getJsonObject("status").getString("code");
				            	if(code.equals("1-200")) {
					            	receipt.confirm();
				            	} else if(code.equals("469")) {
				            		receipt.cancel();
				            	}
				            }
						}
					}			
					
					return new RsEmpty();
				}
		);
	}
}
