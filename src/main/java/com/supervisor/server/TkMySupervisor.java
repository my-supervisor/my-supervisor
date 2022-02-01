package com.supervisor.server;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.SqlTxn;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.Take;
import com.supervisor.domain.User;
import com.supervisor.takes.RqUser;

public final class TkMySupervisor extends TkBaseWrap {

	public TkMySupervisor(final Base base, final Take take) {
		super(
			base,
			req ->
				new SqlTxn(base, req)
					.call(
						(final Base myBase) -> {
							final User user = new RqUser(myBase, req);
							if(new RqUser(myBase, req).isAnonymous()) {
								I18n.register(req);
							} else {
								I18n.register(user.locale());
							}
							return take.act(req);
						}
					)
		);
	}

}
