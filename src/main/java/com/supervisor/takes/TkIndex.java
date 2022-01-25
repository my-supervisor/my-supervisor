package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;

public final class TkIndex extends TkBaseWrap {

	public TkIndex(final Base base) {
		super(
				base,
				req -> {

					return new RsPage(
							I18n.localizeXslt("/xsl/index/page.xsl"),
							req, 
							base,
							()-> new Sticky<>(
								new XeAppend("menu", "home")
							)
					);
				}
		);
	}	
}