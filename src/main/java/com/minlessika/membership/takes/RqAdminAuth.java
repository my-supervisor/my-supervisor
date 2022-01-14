package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.UserWrap;
import com.minlessika.sdk.datasource.Base;
import org.takes.Request;

import javax.mail.AuthenticationFailedException;
import java.io.IOException;

public final class RqAdminAuth extends UserWrap {
    
	/**
     * Ctor.
     * @param bse Base
     * @param req Request
     * @throws IOException 
     * @throws AuthenticationFailedException 
     */
    public RqAdminAuth(final Base base, final Request req) throws IOException {
        super(validateAdminProfile(new RqUser(base, req)));
    }

	private static User validateAdminProfile(final User user) throws IOException {
		
		final Profile profile = user.applications().get(Membership.NAME).profile();
		if(!profile.isAdmin())
			throw new IllegalArgumentException("Accès interdit !");

        return user;
    }
}
