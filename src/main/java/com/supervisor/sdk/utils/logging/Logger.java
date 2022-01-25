package com.supervisor.sdk.utils.logging;

public interface Logger {
	void error(Throwable e);	
	void info(String message);	
	void warn(String message);	
	void debug(String message);	
	void trace(String message);	
	void fatal(Throwable e);
}
