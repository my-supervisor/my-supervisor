package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.BaseUri;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.facets.previous.RsPrevious;

import com.supervisor.domain.User;

public final class TkStart extends TkBaseWrap {

	public TkStart(final Base base) {
		super(
				base,
				req -> {
					
					final User user = new RqUser(base, req);
			        if (user.isAnonymous()) {
			        	return new RsPrevious(
		        			new RsForward(
		        					new RsFlash("Please log in before starting !"), 
		        					"/login"
		        			), 
		        			"/home"
				        );
			        } else {
			        	return new RsForward("/home");
			        }
				}
		);
	}	
}
