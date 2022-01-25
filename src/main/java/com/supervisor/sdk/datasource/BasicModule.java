package com.supervisor.sdk.datasource;

import com.supervisor.sdk.app.info.AppInfo;

public class BasicModule implements Module {

	private final String name;
	private final AppInfo appInfo;
	
	public BasicModule(final String name, final AppInfo appInfo) {
		this.name = name;
		this.appInfo = appInfo;
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public String physicalUrlOf(String resource) {
		return String.format("%s/%s", physicalUrl(), resource);
	}
	
	@Override
	public String virtualUrlOf(String resource) {
		return String.format("%s/%s", virtualUrl(), resource);
	}

	@Override
	public String physicalUrl() {
		return String.format("./src/main/resources/com/%s", name);
	}

	@Override
	public String virtualUrl() {
		return String.format("/com/%s", name);
	}

	@Override
	public AppInfo appInfo() {
		return appInfo;
	}
}
