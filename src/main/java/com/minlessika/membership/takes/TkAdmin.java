package com.minlessika.membership.takes;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.PreviousLocation;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.forward.RsForward;
import org.takes.facets.previous.RsPrevious;

public final class TkAdmin extends TkBaseWrap {

	public TkAdmin(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final String previous = new PreviousLocation(req, true).toString();
					
					return new RsPrevious(
							new RsForward(
					            "/admin/profile"
					        ),
							previous
					);
				}
		);
	}
}
