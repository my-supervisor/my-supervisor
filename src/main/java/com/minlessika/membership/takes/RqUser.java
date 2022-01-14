package com.minlessika.membership.takes;

import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.Users;
import com.minlessika.membership.domain.impl.DmUser;
import com.minlessika.membership.domain.impl.PgUsers;
import com.minlessika.membership.domain.impl.UserWrap;
import com.minlessika.sdk.datasource.Base;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;

import javax.mail.AuthenticationFailedException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

public final class RqUser extends UserWrap {
    
	/**
     * Ctor.
     * @param bse Base
     * @param req Request
     * @throws IOException 
     * @throws AuthenticationFailedException 
     */
    public RqUser(final Base base, final Request req) throws IOException {
        super(user(base, req));
    }

	private static User user(final Base base, final Request request) throws IOException {
		final User user;
        
		final Identity identity = new RqAuth(request).identity();
        if (identity.equals(Identity.ANONYMOUS)) {
        	user = new DmUser(base.select(User.class, 2L));
        }else {
        	Map<String, String> props = identity.properties();
        	Users users = new PgUsers(new DmUser(base.select(User.class, 1L))); 
        	
        	try {
        		user = users.signin(props.get("email"), props.get("password"), true);
        		
			} catch (IllegalArgumentException e) {
				throw new RsForward(            	
	                new RsFlash(e.getLocalizedMessage(), Level.WARNING),
	                "/login?PsByFlag=PsLogout"
	            );
			}       	
        }

        return user;
    }
}
