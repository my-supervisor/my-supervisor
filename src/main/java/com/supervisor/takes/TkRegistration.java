package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.takes.XeRecaptcha;
import com.supervisor.sdk.translation.I18n;
import org.takes.Request;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkRegistration extends TkForm {

	public TkRegistration(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return I18n.localizeXslt("/xsl/registration/page.xsl");
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		List<XeSource> content = new ArrayList<>();
		content.add(itemToShow);
		content.add(new XeAppend("menu", "registration")); 
		content.add(new XeRecaptcha(base.appInfo()));
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(Long id, Request req) throws IOException {
		return XeSource.EMPTY;
	}
}
