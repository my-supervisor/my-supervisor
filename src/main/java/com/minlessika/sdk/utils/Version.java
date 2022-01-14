package com.minlessika.sdk.utils;

import java.io.IOException;

public interface Version {
	
	String value() throws IOException;
	
	boolean isOldThan(String version) throws IOException;
	boolean isNewThan(String version) throws IOException;
	boolean isSameThan(String version) throws IOException;
}
