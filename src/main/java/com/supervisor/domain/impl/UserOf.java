package com.supervisor.domain.impl;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.sdk.datasource.Recordable;

import java.io.IOException;

public final class UserOf extends UserWrap {

	public UserOf(final Base base) throws IOException {
		super(
			new DmUser(
				base.select(
			    		  User.class, 
			    		  base.currentUserId()
				      )
			)
		);
	}
	
	public UserOf(final Recordable entity) throws IOException {
		this(entity.base());
	}

	public <T extends Recordable, D> UserOf(final DomainSet<T, D> entity) throws IOException {
		this(entity.base());
	}
}
