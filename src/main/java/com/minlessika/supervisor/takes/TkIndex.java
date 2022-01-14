package com.minlessika.supervisor.takes;
import org.cactoos.iterable.Sticky;
import org.takes.facets.forward.RsForward;

import com.minlessika.membership.domain.User;
import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Supervisor;

public final class TkIndex extends TkBaseWrap {

	public TkIndex(final Base base) {
		super(
				base,
				req -> {
					final User user = new RqUser(base, req);
					
					if(user.applications().has(Supervisor.NAME)) {
						// show directly home page
						return new RsForward("/home");
					} else {
						// it is the first time
						return new RsPage(
								"/com/supervisor/xsl/index.xsl",
								req, 
								base,
								()-> new Sticky<>()
						);
					}					
				}
		);
	}	
}
