package com.supervisor.domain.impl;

import com.supervisor.domain.Access;
import com.supervisor.domain.AllAccesses;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;

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
