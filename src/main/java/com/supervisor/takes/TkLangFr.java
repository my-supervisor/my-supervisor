package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.PreviousLocation;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.cookies.RsWithCookie;
import org.takes.facets.forward.RsForward;

import java.util.Locale;

public final class TkLangFr extends TkBaseWrap {

	public TkLangFr(Base base) {
		super(
				base,
				req -> {
					
					I18n.register(new Locale("fr", "FR"));
					
					return new RsWithCookie(
								new RsForward(new PreviousLocation(req, "/").toString()), 
								"lang", 
								"fr",
								"Path=/",
								"Expires=Wed, 13 Jan 4040 22:23:01 GMT"
							);
				}
		);
	}
}
