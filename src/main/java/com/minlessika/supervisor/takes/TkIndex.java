package com.minlessika.supervisor.takes;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.sdk.translation.I18n;
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