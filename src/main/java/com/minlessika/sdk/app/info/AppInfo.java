package com.minlessika.sdk.app.info;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public interface AppInfo {
	
	boolean activeRecaptcha();
	String home();
	String recaptchaSiteKey();
	String currentModule();
	String recaptchaSecretKey();
	String sharedDomain();
	String minlessikaDomain();
	boolean activeMailChimp();
	Map<String, String> moduleLinks();	
	
	AppInfo EMPTY = new AppInfo() {
		
		@Override
		public String recaptchaSiteKey() {
			return StringUtils.EMPTY;
		}
		
		@Override
		public String recaptchaSecretKey() {
			return StringUtils.EMPTY;
		}
		
		@Override
		public boolean activeRecaptcha() {
			return false;
		}

		@Override
		public boolean activeMailChimp() {
			return false;
		}

		@Override
		public Map<String, String> moduleLinks() {
			return new HashMap<>();
		}

		@Override
		public String sharedDomain() {
			return StringUtils.EMPTY;
		}

		@Override
		public String minlessikaDomain() {
			return StringUtils.EMPTY;
		}

		@Override
		public String currentModule() {
			return StringUtils.EMPTY;
		}

		@Override
		public String home() {
			return StringUtils.EMPTY;
		}
	};
}
