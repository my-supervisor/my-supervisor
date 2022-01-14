package com.minlessika.supervisor.domain;

public interface DataFieldValue {
	boolean isValid();
	void validate();
	String cleaned();
}
