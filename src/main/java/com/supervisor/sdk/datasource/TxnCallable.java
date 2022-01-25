package com.supervisor.sdk.datasource;

@FunctionalInterface
public interface TxnCallable<T> {
	T call(Base base) throws Exception;
}
