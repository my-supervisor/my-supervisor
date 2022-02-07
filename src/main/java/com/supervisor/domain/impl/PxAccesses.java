package com.supervisor.domain.impl;

import com.supervisor.domain.Access;
import com.supervisor.domain.Accesses;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxAccesses extends DomainRecordables<Access, Accesses> implements Accesses {
	
	public PxAccesses(final Base base) throws IOException {
		this(base.select(Access.class));
	}
	
	public PxAccesses(final RecordSet<Access> source) throws IOException {
		super(PxAccess.class, source);
	}

	@Override
	protected Accesses domainSetOf(final RecordSet<Access> source) throws IOException {
		return new PxAccesses(source);
	}
	
	@Override
	public Access add(String code, String name) throws IOException {
		
		source.isRequired(Access::name, name);	
		source.isRequired(Access::code, code);
		
		source.mustBeUnique(Access::code, code.toUpperCase());
		
		Record<Access> record = source.entryOf(Access::name, name)
				  					  .entryOf(Access::code, code.toUpperCase())
						      		  .add();
		
		return domainOf(record);
	}

	@Override
	public Access access(String code) throws IOException {
		Accesses accesses = where(Access::code, code);
		
		if(accesses.isEmpty())
			throw new IllegalArgumentException(String.format("L'accès de code %s n'existe pas !", code));
		
		return accesses.first();
	}
	
	
}
