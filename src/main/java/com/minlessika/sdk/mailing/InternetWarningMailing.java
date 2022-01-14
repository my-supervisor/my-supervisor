package com.minlessika.sdk.mailing;

import com.minlessika.sdk.utils.Internet;

import java.io.IOException;

public final class InternetWarningMailing extends MailingWrap {

	public InternetWarningMailing() throws IOException {
		this(new BasicMailing());
	}
	
	public InternetWarningMailing(Mailing origin) {
		super(new Mailing() {
			
			@Override
			public void send(String to, String objet, String message) throws IOException {
				if(!new Internet().isAvailable()) {
					throw new IllegalArgumentException("Internet is not available !");
				}
				
				origin.send(to, objet, message);
			}
		});
	}

}
