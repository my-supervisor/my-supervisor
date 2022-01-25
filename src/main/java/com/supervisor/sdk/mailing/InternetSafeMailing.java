package com.supervisor.sdk.mailing;

import com.supervisor.sdk.utils.Internet;

import java.io.IOException;

public final class InternetSafeMailing extends MailingWrap {

	public InternetSafeMailing() throws IOException {
		this(new BasicMailing());
	}
	
	public InternetSafeMailing(Mailing origin) {
		super(new Mailing() {
			
			@Override
			public void send(String to, String objet, String message) throws IOException {
				if(new Internet().isAvailable()) {
					origin.send(to, objet, message);
				}
			}
		});
	}

}
