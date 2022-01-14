package com.minlessika.sdk.websockets;

import org.takes.Request;

import java.io.IOException;

public interface WebSocketServer {
	
	String host();
	int port();	
	String bare(Request req);
	
	void start() throws IOException;
	
	boolean hasHeader(Class<?> serviceClazz, String key, String value);
	void publish(Class<?> serviceClazz, Object data, String headerKey, String headerValue) throws IOException;
}
