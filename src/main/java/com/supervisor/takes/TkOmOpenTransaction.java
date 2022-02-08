package com.supervisor.takes;

import com.supervisor.billing.MobileMoney;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.minlessika.rs.RsRedirectPost;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rq.RqHref;
import org.takes.rs.RsWithHeader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TkOmOpenTransaction extends TkBaseWrap {

	public TkOmOpenTransaction(Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);
					final User user = module.user();
					
					final UUID id = UUID.fromString(new RqHref.Smart(req).single("request"));
					
					final PaymentRequest paymentRequest = user.paymentRequests().get(id);
					final MobileMoney method = (MobileMoney)module.paymentMethods().where(PaymentMethod::tag, "OM").first();
					
					// create a PENDING receipt
					final PaymentReceipt receipt = method.request(paymentRequest);				
					
					// redirect to OM page
	            	final Map<String, String> omPageBody = new HashMap<>();
	            	omPageBody.put("merchantid", method.username());
	            	
	            	final int amount = (int)Math.round(receipt.amount());
	            	omPageBody.put("amount", String.format("%s", amount));
	            	
	            	final String clearWord = String.format("%s::%s", user.id(), receipt.id());
					final String sessionId = new String(
									            Base64.getEncoder().encode(clearWord.getBytes(StandardCharsets.UTF_8))
									         );
	            	omPageBody.put("sessionid", sessionId);
	            	
	            	omPageBody.put("purchaseref", receipt.reference());
	            	omPageBody.put("description", receipt.object());
	            	
	            	final String token = receipt.metadata().get("token");
	            	omPageBody.put("token", token);
	            	
					return new RsWithHeader(
        				new RsRedirectPost(
        					"https://ompay.orange.ci/e-commerce/", 
        					omPageBody
        				), 
        				"Accept-Language", 
        				user.preferredLanguage().isoCode()
        		    );       			        
				}
		);
	}
}
