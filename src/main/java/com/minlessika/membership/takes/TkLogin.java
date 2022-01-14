package com.minlessika.membership.takes;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.sdk.takes.XeRecaptcha;
import com.minlessika.sdk.translation.I18n;
import org.takes.Request;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkLogin extends TkForm {

	public TkLogin(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return I18n.localizeXslt("/com/membership/xsl/login/page.xsl");
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		List<XeSource> content = new ArrayList<>();
		content.add(itemToShow);
		content.add(new XeAppend("menu", "login"));
		content.add(new XeRecaptcha(base.appInfo()));
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(Long id, Request req) throws IOException {
		return XeSource.EMPTY;
	}
}
