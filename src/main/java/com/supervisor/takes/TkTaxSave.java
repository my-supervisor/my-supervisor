package com.supervisor.takes;

import com.supervisor.billing.Tax;
import com.supervisor.billing.Taxes;
import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
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
