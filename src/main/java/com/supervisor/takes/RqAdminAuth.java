package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.Profile;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.UserWrap;
import com.supervisor.sdk.datasource.Base;
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
		if(!user.profile().isAdmin())
			throw new IllegalArgumentException("Accès interdit !");

        return user;
    }
}
