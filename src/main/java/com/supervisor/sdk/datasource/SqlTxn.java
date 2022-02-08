package com.supervisor.sdk.datasource;

import com.supervisor.domain.User;
import com.supervisor.sdk.utils.OptUUID;
import com.supervisor.sdk.utils.logging.Logger;
import com.supervisor.sdk.utils.logging.MLogger;
import org.takes.HttpException;
import org.takes.Request;

import java.net.HttpURLConnection;
import java.util.UUID;

public final class SqlTxn implements Txn {
	
	private static final Logger logger = new MLogger(SqlTxn.class);
	
	private final Base base;
	private final Request request;
	private final UUID userId;
	
	public SqlTxn(final Base base) {
		this(base, User.ANONYMOUS_ID);
	}
	
	public SqlTxn(final Base base, final Request request) {
		this.base = base;
		this.userId = new OptUUID("0").value();
		this.request = request;
	}
	
	public SqlTxn(final Base base, final UUID userId) {
		this.base = base;
		this.userId = userId;
		this.request = null;
	}
	
	public <T> T call(TxnCallable<T> callable) throws Exception {
		T result = null;

		try {		
			
			if(request == null) {
				base.start(true, userId);
			} else {
				base.start(true, request);
			}
			
			result = callable.call(base);
			base.commit();
		} catch (IllegalArgumentException e) {
			base.rollback();
			logger.error(e); 
			throw new HttpException(
		                HttpURLConnection.HTTP_BAD_REQUEST,
		                String.format(
		                    "IllEg:%s",
		                    e.getLocalizedMessage()
		                )
		          );
		} catch (HttpException e) {
			base.rollback();
			logger.error(e); 
			throw e;
		} catch(Exception e) {
			base.rollback();
			logger.error(e);
			throw new HttpException(
		                HttpURLConnection.HTTP_INTERNAL_ERROR,
		                String.format(
		                    e.getLocalizedMessage()
		                )
			      );
		} finally {
			base.close();
		}
		
		return result;
	}
}
