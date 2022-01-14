package com.minlessika.supervisor.domain.bi;

public interface BiValue {
	BiRow row();
	String name();
	<T> T value();
}
