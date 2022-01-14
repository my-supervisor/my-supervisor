package com.minlessika.membership.takes;

import com.minlessika.membership.billing.Tax;
import com.minlessika.membership.billing.Taxes;
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

public final class TkTaxSave extends TkBaseWrap {

	public TkTaxSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);
					final Taxes taxes = module.taxes();
					
					Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					
					final String name = form.single("name");
					final double value = Double.parseDouble(form.single("value"));		
					
					final Tax item = taxes.get(id);
					item.update(name, item.shortName(), item.type(), value, item.valueType());
					
					final String msg = String.format("La taxe %s a été modifiée avec succès !", item.name());
					
					return new RsForward(
			            	new RsFlash(
				                msg,
				                Level.FINE
				            ),
				            "/admin/tax"
					    );
				}
		);
	}
}
