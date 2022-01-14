package com.minlessika.membership.takes;

import com.minlessika.membership.billing.CreditCard;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.billing.impl.AmountConvertedInXOF;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.rs.RsRedirectPost;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.rq.RqHref;
import org.takes.rs.RsWithHeader;

import java.util.HashMap;
import java.util.Map;

public final class TkCinetPayOpenTransaction extends TkBaseWrap {

	public TkCinetPayOpenTransaction(Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);
					final User user = module.user();
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("request"));
					
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
