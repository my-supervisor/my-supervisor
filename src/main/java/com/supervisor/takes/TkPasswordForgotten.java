package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.takes.XeRecaptcha;
import org.takes.Request;
import org.takes.rs.xe.XeSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkPasswordForgotten extends TkForm {

	public TkPasswordForgotten(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/password_forgotten.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		List<XeSource> content = new ArrayList<>();
		content.add(itemToShow);
		content.add(new XeRecaptcha(base.appInfo()));
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(Long id, Request req) throws IOException {
		return XeSource.EMPTY;
	}
}
