package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.Recordable;

import java.io.IOException;

public final class UserAdmin extends UserWrap {

	public UserAdmin(final Base base) throws IOException {
		super(
			new DmUser(base.select(User.class).get(1L))
		);
	}

	public UserAdmin(final User currentUser) throws IOException {
		super(
			new DmUser(currentUser.listOf(User.class).get(1L))
		);
	}
	
	public UserAdmin(final Recordable entity) throws IOException {
		super(
			new DmUser(
					entity.base()
					      .select(
				    		  User.class, 
				    		  1L
					      )
				)
		);
	}
}
