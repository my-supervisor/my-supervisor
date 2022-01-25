package com.supervisor.sdk.datasource;

public interface Txn {
	<T> T call(TxnCallable<T> callable) throws Exception;
}
