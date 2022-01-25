package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.PreviousLocation;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.cookies.RsWithCookie;
import org.takes.facets.forward.RsForward;

import java.util.Locale;

public final class TkLangEn extends TkBaseWrap {

	public TkLangEn(Base base) {
		super(
				base,
				req -> {
					
					I18n.register(new Locale("en", "US"));
					
					return new RsWithCookie(
								new RsForward(new PreviousLocation(req, "/").toString()), 
								"lang", 
								"en",
								"Path=/",
								"Expires=Wed, 13 Jan 4040 22:23:01 GMT"
							);
				}
		);
	}
}
