package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.sdk.datasource.Recordable;

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
