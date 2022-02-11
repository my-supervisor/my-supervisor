package com.supervisor.sdk.secure;

public interface Recaptcha {
	String siteKey();
	String secretKey();
	boolean isActive();
	boolean isValid(String recaptchaResponse);
	void validate(String recaptchaResponse);
}
