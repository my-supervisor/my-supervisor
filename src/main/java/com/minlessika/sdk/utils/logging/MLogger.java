package com.minlessika.sdk.utils.logging;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;

import java.io.File;

public final class MLogger implements Logger {

	private final org.apache.logging.log4j.Logger myLogger;
	
	static {
		final File log4j2File = new File("log4j2.xml");
		if(log4j2File.exists()) {
			System.setProperty("log4j2.configurationFile", log4j2File.toURI().toString());
		}	    
	}
	
	public MLogger(final Class<?> clazz) {
		this.myLogger = LogManager.getLogger(clazz);
	}
	
	public void error(Throwable e) {
		myLogger.error("{}", ExceptionUtils.getStackTrace(e));
	}
	
	public void info(String message) {
		myLogger.info(message);
	}
	
	public void warn(String message) {
		myLogger.warn(message);
	}
	
	public void debug(String message) {
		myLogger.debug(message);
	}
	
	public void trace(String message) {
		myLogger.trace(message);
	}
	
	public void fatal(Throwable e) {
		myLogger.fatal("{}", ExceptionUtils.getStackTrace(e));
	}
}
