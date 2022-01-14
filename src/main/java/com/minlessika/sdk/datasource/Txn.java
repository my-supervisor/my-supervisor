package com.minlessika.sdk.datasource;

public interface Txn {
	<T> T call(TxnCallable<T> callable) throws Exception;
}
