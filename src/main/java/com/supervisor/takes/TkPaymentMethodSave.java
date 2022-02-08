package com.supervisor.takes;

import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentMethods;
import com.supervisor.billing.PaymentType;
import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.UUID;
import java.util.logging.Level;

public final class TkPaymentMethodSave extends TkBaseWrap {

	public TkPaymentMethodSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);
					final PaymentMethods methods = module.paymentMethods();
					
					UUID id = UUID.fromString(new RqHref.Smart(req).single("id"));
					
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
