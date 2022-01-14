package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XeMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import java.util.logging.Level;

public final class TkHome extends TkBaseWrap {

	public TkHome(final Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);
					final XeSource xeMembership = new XeMembership(module);

					final User user = new RqUser(base, req);
					
					// subscribe to modules
					user.applications().add(Membership.NAME);
					user.applications().add("supervisor");
					
					if(!user.billingAddress().isComplete()) {
						return new RsForward(
								new RsFlash(
								    "Veuillez SVP complétez votre profil avant de continuer !",
					                Level.FINE
					            ),
					            "/user-profile/edit"
						    );
					}
					
					return new RsPage(
								"/com/membership/xsl/home.xsl",
								req, 
								base,
								()-> new Sticky<>(
										new XeAppend("menu", "home"),
										xeMembership
								)
							);			
				}
		);
	}	
}
