package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.RegistrationRequest;
import com.supervisor.domain.RegistrationRequests;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.domain.impl.UserAdmin;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.mailing.InternetSafeMailing;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;

public final class TkConfirmRegistering extends TkBaseWrap {

	public TkConfirmRegistering(Base base) {
		super(
				base,
				req -> {
					Membership module = new DmMembership(base);
					
					final UUID guid = UUID.fromString(
											new String(
													Base64.getDecoder().decode(new RqHref.Smart(req).single("key")), 
													StandardCharsets.UTF_8
											)
									  );
					
					RegistrationRequests myRequests = module.registrationRequests().where(RegistrationRequest::id, guid);
					
					if(myRequests.isEmpty())
						throw new IllegalArgumentException("The link sent is not valid !");
					
					RegistrationRequest myRequest = myRequests.first();	
					User user = myRequest.process();
					user.activate(true);					

					new InternetSafeMailing().send(
							user.address().email(), 
							"Activation of your Minlessika account", 
							"Your account has been activated with success !"
					);
					
					final String adminMessage = String.format("A new member (%s) has come !", user.name());
					new InternetSafeMailing().send(
							new UserAdmin(base).address().email(), 
							"New Minlessika member", 
							adminMessage
					);
					
					return new RsForward(
			            		new RsFlash(
					                "Your account has been successfully activated !",
					                Level.FINE
					            ),
					            "/login"
						    );
				}
		);
	}
}
