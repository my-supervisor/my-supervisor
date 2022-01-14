package com.minlessika.sdk.takes;

import org.apache.commons.lang.StringUtils;
import org.takes.Request;
import org.takes.rq.RqHeaders;

import java.io.IOException;

public final class PreviousLocation {
	
	private final Request req;
	private final String defaultLocation;
	private final boolean completeLocation;
	
	public PreviousLocation(final Request req) {
		this(req, false, "/");
	}
	
	public PreviousLocation(final Request req, boolean completeLocation) {
		this(req, completeLocation, "/");
	}
	
	public PreviousLocation(final Request req, final String defaultLocation) {
		this(req, false, defaultLocation);
	}
	
	public PreviousLocation(final Request req, boolean completeLocation, final String defaultLocation) {
		this.req = req;
		this.defaultLocation = defaultLocation;
		this.completeLocation = completeLocation;
	}
	
	@Override
	public String toString() {
		
		try {
			final String uri = new RqHeaders.Smart(req).single("Referer", "NONE");
            
    		String url;
    		if(uri.equalsIgnoreCase("NONE"))
    			url = defaultLocation;
    		else {
    			if(completeLocation) {
    				url = uri;
    			} else {
    				String baseUri = new BaseUri(req).toString();
        			url = StringUtils.remove(uri, baseUri);
    			}    			
    		}
    		
    		return url;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	    
	}
}
