package com.supervisor.sdk.secure;

public interface Recaptcha {
	String siteKey();
	String secretKey();
	
	boolean isValid(String recaptchaResponse);
	void validate(String recaptchaResponse);
}
