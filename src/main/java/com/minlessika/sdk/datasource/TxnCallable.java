package com.minlessika.sdk.datasource;

@FunctionalInterface
public interface TxnCallable<T> {
	T call(Base base) throws Exception;
}
