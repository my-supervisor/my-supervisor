package com.minlessika.sdk.datasource;

import com.minlessika.sdk.app.info.AppInfo;

public interface Module {
	
	String name();
	String physicalUrl();
	String virtualUrl();
	String physicalUrlOf(String resource);
	String virtualUrlOf(String resource);
	
	AppInfo appInfo();
}
