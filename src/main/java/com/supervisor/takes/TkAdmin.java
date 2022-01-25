package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.PreviousLocation;
import com.supervisor.sdk.takes.TkBaseWrap;
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
