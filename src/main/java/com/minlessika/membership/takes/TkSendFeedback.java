package com.minlessika.membership.takes;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.mailing.InternetWarningMailing;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkSendFeedback extends TkBaseWrap {

	public TkSendFeedback(Base base) {
		super(
				base,
				req -> {
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String object = form.single("object");
					final String message = form.single("message");
					
					final User user = new RqUser(base, req);
					
					new InternetWarningMailing().send(
							user.address().email(), 
							object, 
							message
					);
					
					final String msg = "Your mail has been sent to us with success !";
					
					return new RsForward(
						new RsFlash(
			                msg,
			                Level.FINE
			            ),
						"/home"
				    );
				}
		);
	}
}
