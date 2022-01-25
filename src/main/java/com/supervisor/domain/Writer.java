package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.Recordable;

public interface Writer<T extends Recordable> {
	T copy() throws IOException;
}
