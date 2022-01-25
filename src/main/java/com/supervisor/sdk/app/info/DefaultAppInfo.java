package com.supervisor.sdk.app.info;

import java.util.HashMap;
import java.util.Map;

public final class DefaultAppInfo implements AppInfo {
	
	@Override
	public boolean activeRecaptcha() {
		return false;
	}

	@Override
	public String recaptchaSiteKey() {
		return null;
	}

	@Override
	public String currentModule() {
		return "NONE";
	}

	@Override
	public String recaptchaSecretKey() {
		return null;
	}

	@Override
	public String sharedDomain() {
		return null;
	}

	@Override
	public String minlessikaDomain() {
		return null;
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
	public String home() {
		return null;
	}

}
