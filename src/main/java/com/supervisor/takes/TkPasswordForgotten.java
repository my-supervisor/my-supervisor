package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.takes.XeRecaptcha;
import com.supervisor.sdk.utils.OptUUID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.takes.Request;
import org.takes.rs.xe.XeSource;

public final class TkPasswordForgotten extends TkForm {

	private final Recaptcha recaptcha;
	public TkPasswordForgotten(final Base base, final Recaptcha recaptcha) {
		super(base);
		this.recaptcha = recaptcha;
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/password_forgotten.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		List<XeSource> content = new ArrayList<>();
		content.add(itemToShow);
		content.add(new XeRecaptcha(recaptcha));
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(OptUUID id, Request req) throws IOException {
		return XeSource.EMPTY;
	}
}
