package com.minlessika.membership.takes;

import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentMethods;
import com.minlessika.membership.billing.PaymentType;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkPaymentMethodSave extends TkBaseWrap {

	public TkPaymentMethodSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);
					final PaymentMethods methods = module.paymentMethods();
					
					Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					
					final String name = form.single("name");		
					final boolean enabled = Boolean.parseBoolean(form.single("enabled"));		
					
					final PaymentMethod item = methods.get(id);
					item.rename(name);
					item.enable(enabled);
					
					final PaymentType type = item.type();
					if(type == PaymentType.MOBILE_MONEY || type == PaymentType.ASSOCIATE) {
						
						final String username = form.single("username");
						final String password = form.single("password");
						item.changeCredentials(username, password);
					}
					
					final String msg = String.format("Le mode de paiement %s a été modifié avec succès !", item.name());
					
					return new RsForward(
			            	new RsFlash(
				                msg,
				                Level.FINE
				            ),
				            "/admin/payment-method"
					    );
				}
		);
	}
}
