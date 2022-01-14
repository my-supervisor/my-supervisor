
package com.minlessika.membership.takes;

import com.minlessika.membership.billing.CreditCard;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.domain.impl.DmUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsEmpty;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public final class TkCinetPayTransactionCallback extends TkBaseWrap {

	public TkCinetPayTransactionCallback(final Base base) { 
		super(
				base,
				req -> {
					RecordSet<User> source = base.select(User.class);
					User user = new DmUser(source.get(1L)); // prendre l'utilisateur Minlessika
					
					final Membership membership = new DmMembership(base, user);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));										
					final CreditCard method = (CreditCard)membership.paymentMethods().where(PaymentMethod::tag, "CINETPAY").first();
					
					final String siteID = form.single("cpm_site_id");
					if(!method.username().equals(siteID)) // Provider error
						return new RsEmpty();
					
					final String token = form.single("signature");
					if(!method.pendingPayments().contains(token))
						return new RsEmpty();
					
					final String transactionId = form.single("cpm_trans_id");
					
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
								method.username(),
								method.password(),
								transactionId
					   );			
						
						wr.writeBytes(content);
						wr.flush();
					} catch(Exception e) {					
						throw new IllegalArgumentException(e.getLocalizedMessage());
					}
					
					try(InputStreamReader in = new InputStreamReader(con.getInputStream())) {
						try(JsonReader jsonReader = Json.createReader(in)){
							final JsonObject jsonObject = jsonReader.readObject().getJsonObject("transaction");
							final PaymentReceipt receipt = method.pendingPayments().get(token);
							final Map<String, String> metadata = receipt.metadata();
							metadata.put("cpm_payid", jsonObject.getString("cpm_payid"));
							metadata.put("cpm_payment_date", jsonObject.getString("cpm_payment_date"));
							metadata.put("cpm_payment_time", jsonObject.getString("cpm_payment_time"));
							metadata.put("cpm_error_message", jsonObject.getString("cpm_error_message"));
							metadata.put("cpm_ipn_ack", jsonObject.getString("cpm_ipn_ack"));
							metadata.put("created_at", jsonObject.getString("created_at"));
							metadata.put("updated_at", jsonObject.getString("updated_at"));
							metadata.put("cpm_result", jsonObject.getString("cpm_result"));
							metadata.put("cpm_trans_status", jsonObject.getString("cpm_trans_status"));
							metadata.put("buyer_name", jsonObject.getString("buyer_name"));
							
							final String code = jsonObject.getString("cpm_result");							
							switch (code) {
								case "00":
									final double amount = Double.parseDouble(jsonObject.getString("cpm_amount"));
									final String signature = jsonObject.getString("signature");
									if(signature.equals(token) && receipt.amount() == amount) {
										metadata.put("cpm_payid", jsonObject.getString("cpm_payid"));
										receipt.updateMetadata(metadata);
										receipt.confirm();										
									}									
									break;
								case "607":
								case "623":
								case "624":
									// still wait
									break;
								default:
									receipt.updateMetadata(metadata);
									receipt.cancel();
							}				
						}
					} catch(Exception e) {
						throw new IllegalArgumentException(e.getLocalizedMessage());
					}					
					
					return new RsEmpty();
				}
		);
	}
}
