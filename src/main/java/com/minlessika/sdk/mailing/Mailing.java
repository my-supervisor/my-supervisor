package com.minlessika.sdk.mailing;

import java.io.IOException;

public interface Mailing {
	void send(String to, String objet, String message) throws IOException;
}
