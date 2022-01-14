package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.Accesses;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxAccesses extends DomainRecordables<Access, Accesses> implements Accesses {

	private final String module;
	
	public PxAccesses(final Base base, final String module) throws IOException {
		this(base.select(Access.class), module);
	}
	
	public PxAccesses(final RecordSet<Access> source, final String module) throws IOException {
		super(PxAccess.class, source);
		
		this.module = module;
		this.source = source.where(Access::module, module);
	}

	@Override
	protected Accesses domainSetOf(final RecordSet<Access> source) throws IOException {
		return new PxAccesses(source, module);
	}
	
	@Override
	public Access add(String code, String name) throws IOException {
		
		source.isRequired(Access::name, name);	
		source.isRequired(Access::code, code);
		
		source.mustBeUnique(Access::code, code.toUpperCase());
		
		Record<Access> record = source.entryOf(Access::name, name)
				  					  .entryOf(Access::code, code.toUpperCase())
				  					  .entryOf(Access::module, module)
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
