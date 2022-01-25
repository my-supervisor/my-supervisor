package com.supervisor.domain.impl;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Recordable;

import java.io.IOException;

public final class LastModifierOf extends UserWrap {

	public LastModifierOf(final Recordable entity) throws IOException {
		super(
			new DmUser(
				entity.base()
				      .select(
			    		  User.class, 
			    		  entity.lastModifierId()
				      )
			)
		);
	}

}
