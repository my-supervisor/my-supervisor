package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.translation.I18n;
import java.io.IOException;
import org.cactoos.iterable.IterableOf;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;

public final class TkLogin implements Take {

	@Override
	public Response act(final Request req) throws IOException {
		return new RsAnonymousPage(
			I18n.localizeXslt("/xsl/login/page.xsl"),
			req,
			new XeChain(
				new XeAppend("menu", "login"),
				new XeAppend("lang", I18n.locale().getLanguage())
			)
		);
	}
}
