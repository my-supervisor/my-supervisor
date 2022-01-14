package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.Recordable;

public interface Writer<T extends Recordable> {
	T copy() throws IOException;
}
