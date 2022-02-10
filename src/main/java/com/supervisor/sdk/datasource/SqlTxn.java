package com.supervisor.sdk.datasource;

import com.supervisor.domain.User;
import com.supervisor.sdk.utils.logging.Logger;
import com.supervisor.sdk.utils.logging.MLogger;
import org.takes.HttpException;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.UUID;

public final class SqlTxn implements Txn {
	
	private static final Logger logger = new MLogger(SqlTxn.class);
	
	private final Base base;
	private final UUID uid;

	public SqlTxn(final Base base, final Request request) throws IOException {
		this(base, userFrom(request));
	}
	
	public SqlTxn(final Base base, final UUID uid) {
		this.base = base;
		this.uid = uid;
	}
	
	public <T> T call(TxnCallable<T> callable) throws Exception {
		try {
			base.start(true, uid);
			T result = callable.call(base);
			base.commit();
			return result;
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
	}

	private static UUID userFrom(final Request req) throws IOException {
		final UUID uid;
		final Identity identity = new RqAuth(req).identity();
		if (identity.equals(Identity.ANONYMOUS)) {
			uid = User.ANONYMOUS_ID;
		}else {
			Map<String, String> props = identity.properties();
			uid = UUID.fromString(props.get("id"));
		}
		return uid;
	}
}
