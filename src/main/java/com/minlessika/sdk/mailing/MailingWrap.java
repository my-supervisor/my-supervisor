package com.minlessika.sdk.mailing;

import java.io.IOException;

public class MailingWrap implements Mailing {

	private final Mailing origin;
	
	public MailingWrap(final Mailing origin) {
		this.origin = origin;
	}
	
	@Override
	public void send(String to, String object, String message) throws IOException {
		origin.send(to, object, message);
	}
}
