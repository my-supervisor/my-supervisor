package com.supervisor.domain.impl;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.Recordable;

import java.io.IOException;

public final class UserAnonymous extends UserWrap {

	public UserAnonymous(final Base base) throws IOException {
		super(
			new DmUser(base.select(User.class).get(User.ANONYMOUS_ID))
		);
	}

	public UserAnonymous(final User currentUser) throws IOException {
		super(
			new DmUser(currentUser.listOf(User.class).get(User.ANONYMOUS_ID))
		);
	}
	
	public UserAnonymous(final Recordable entity) throws IOException {
		super(
			new DmUser(
					entity.base()
					      .select(
				    		  User.class, 
				    		  User.ANONYMOUS_ID
					      )
				)
		);
	}
}
