package com.supervisor.domain.bi;

public interface BiValue {
	BiRow row();
	String name();
	<T> T value();
}
