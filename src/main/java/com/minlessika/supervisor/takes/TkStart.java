package com.minlessika.supervisor.takes;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.facets.previous.RsPrevious;

import com.minlessika.membership.domain.User;
import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.BaseUri;
import com.minlessika.sdk.takes.TkBaseWrap;

public final class TkStart extends TkBaseWrap {

	public TkStart(final Base base) {
		super(
				base,
				req -> {
					
					final User user = new RqUser(base, req);
			        if (user.isAnonymous()) {
			        	
			        	final String loginUri = String.format("%s/login", base.appInfo().minlessikaDomain());
			        	final String homeUri = String.format("%s/home", new BaseUri(req).toString());

			        	return new RsPrevious(
		        			new RsForward(
		        					new RsFlash("Please log in before starting !"), 
		        					loginUri
		        			), 
		        			homeUri
				        );
			        } else {
			        	return new RsForward("/home");
			        }
				}
		);
	}	
}
