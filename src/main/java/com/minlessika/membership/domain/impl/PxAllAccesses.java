package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.AllAccesses;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxAllAccesses extends DomainRecordables<Access, AllAccesses> implements AllAccesses {

	public PxAllAccesses(final Base base) throws IOException {
		this(base.select(Access.class));
	}
	
	public PxAllAccesses(final RecordSet<Access> source) {
		super(PxAccess.class, source);
	}

	@Override
	public Access access(String code) throws IOException {
		AllAccesses accesses = where(Access::code, code);
		
		if(accesses.isEmpty())
			throw new IllegalArgumentException(String.format("L'accès de code %s n'existe pas !", code));
		
		return accesses.first();
	}
	
	
}
