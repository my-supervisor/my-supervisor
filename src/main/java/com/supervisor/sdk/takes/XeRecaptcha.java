package com.supervisor.sdk.takes;

import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public final class XeRecaptcha extends XeWrap {

	public XeRecaptcha(final boolean enable, final CharSequence sitekey) {
	        super(
				() -> new Directives()
					.add("recaptcha")
					.add("active").set(enable).up()
					.add("site-key").set(sitekey).up()
					.up()
			);
	    }
}
