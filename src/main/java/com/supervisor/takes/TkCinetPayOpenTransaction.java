package com.supervisor.takes;

import com.supervisor.billing.CreditCard;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.billing.impl.AmountConvertedInXOF;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.minlessika.rs.RsRedirectPost;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rq.RqHref;
import org.takes.rs.RsWithHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TkCinetPayOpenTransaction extends TkBaseWrap {

	public TkCinetPayOpenTransaction(Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);
					final User user = module.user();
					
					final UUID id = UUID.fromString(new RqHref.Smart(req).single("request"));
					
					final PaymentRequest payRequest = user.paymentRequests().get(id);
					final CreditCard method = (CreditCard)module.paymentMethods().where(PaymentMethod::tag, "CINETPAY").first();
					
					// create a PENDING receipt
					final PaymentReceipt receipt = method.request(payRequest);					
	            	
	            	// redirect to CINETPAY page
					final Map<String, String> pageBody = new HashMap<>();
	            	pageBody.put("cpm_amount", Integer.toString((int)new AmountConvertedInXOF(receipt.amount(), receipt.currency()).value()));
	            	pageBody.put("cpm_currency", "CFA");
	            	pageBody.put("cpm_site_id", method.username());
	            	pageBody.put("cpm_trans_id", receipt.reference());
	            	pageBody.put("cpm_trans_date", receipt.creationDate().toString());
	            	pageBody.put("cpm_payment_config", "SINGLE");
	            	pageBody.put("cpm_page_action", "PAYMENT");
	            	pageBody.put("cpm_version", "V1");
	            	pageBody.put("cpm_language", user.preferredLanguage().isoCode());
	            	pageBody.put("cpm_designation", receipt.object());
	            	pageBody.put("apikey", method.password());
	            	pageBody.put("signature", receipt.metadata().get("token"));
	            	pageBody.put("cpm_return_mode", "POST"); 
	            	
					return new RsWithHeader(
        				new RsRedirectPost(
        					" https://secure.cinetpay.com", 
        					pageBody
        				), 
        				"Accept-Language", 
        				user.preferredLanguage().isoCode()
        		    );     					
				}
		);
	}
}
