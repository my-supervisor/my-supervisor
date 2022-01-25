package com.supervisor.sdk.takes;

import org.takes.Request;
import org.takes.rq.RqHeaders;

import java.io.IOException;
import java.util.Iterator;

public final class BaseUri {
	
	private final Request req;
	private final int port;
	
	public BaseUri(final Request req) {
		this(req, 0);
	}
	
	public BaseUri(final Request req, final int port) {
		this.req = req;
		this.port = port;
	}
	
	@Override
	public String toString() {
		
		try {
			Iterator<String> hosts = new RqHeaders.Base(req)
			        .header("host").iterator();
			
			final Iterator<String> protos = new RqHeaders.Base(req)
			        .header("x-forwarded-proto").iterator();
		    final String host;
		    if (hosts.hasNext()) {
		        host = hosts.next().trim();
		    } else {
		        host = "localhost";
		    }
		    final String proto;
		    if (protos.hasNext()) {
		        proto = protos.next().trim();
		    } else {
		        proto = "http";
		    }
		    
		    if(host.contains("localhost") && port > 0) {
	        	 return String.format("%s://localhost:%d", proto, port);
	         }else {
	        	 return String.format("%s://%s", proto, host);
	         }

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	    
	}
}
