package com.supervisor.sdk.secure;

public interface EncryptedWord {
	String value();
	String salt();
	boolean verify(String word, boolean secure);
}
