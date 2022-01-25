package com.supervisor.sdk.datasource;

public interface BaseCredentials {
	String host();
	String basename();
	String username();
	String password();
	int port();
}
