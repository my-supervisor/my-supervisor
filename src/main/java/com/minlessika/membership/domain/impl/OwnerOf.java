package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Recordable;

import java.io.IOException;

public final class OwnerOf extends UserWrap {

	public OwnerOf(final Recordable entity) throws IOException {
		super(
			new DmUser(
				entity.base()
				      .select(
			    		  User.class, 
			    		  entity.ownerId()
				      )
			)
		);
	}

}
