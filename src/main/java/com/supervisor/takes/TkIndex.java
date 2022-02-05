package com.supervisor.takes;

import com.supervisor.sdk.translation.I18n;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;

public final class TkIndex implements Take {

	@Override
	public Response act(Request req) throws Exception {
		return new RsAnonymousPage(
			I18n.localizeXslt("/xsl/index/page.xsl"),
			req,
			new XeChain(
				new XeAppend("menu", "home")
			)
		);
	}
}