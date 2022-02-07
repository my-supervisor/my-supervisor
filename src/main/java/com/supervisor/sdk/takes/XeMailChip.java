package com.supervisor.sdk.takes;

import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public final class XeMailChip extends XeWrap {

	public XeMailChip(final CharSequence node, final boolean enable) {
	        super(
				() -> new Directives()
					.add(node)
					.add("active").set(enable).up()
					.up()
			);
	    }
}
