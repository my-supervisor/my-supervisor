package com.supervisor.takes;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkPasswordSave extends TkBaseWrap {

	public TkPasswordSave(Base base) {
		super(
				base,
				req -> {
					final User user = new RqUser(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String actualPassword = form.single("actual_password");
					final String newPassword = form.single("new_password");
					final String newPasswordConfirmed = form.single("new_password_confirmed");
							
					user.changePassword(actualPassword, newPassword, newPasswordConfirmed); 
					
					final String msg = I18n.localizeText("your_password_has_been_successfully_changed");
					
					return new RsForward(
						new RsFlash(
			                msg,
			                Level.FINE
			            ),
						new RqHref.Smart(req).href().bare() + "?PsByFlag=PsLogout"
				    );
				}
		);
	}
}
