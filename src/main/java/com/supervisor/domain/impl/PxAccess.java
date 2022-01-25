package com.supervisor.domain.impl;

import com.supervisor.domain.Access;
import com.supervisor.domain.AccessParam;
import com.supervisor.domain.AccessParams;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxAccess extends DomainRecordable<Access> implements Access {

	public PxAccess(final Record<Access> source) throws IOException {
		super(source);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(Access::code); 
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Access::name);
	}

	@Override
	public AccessParams parameters() throws IOException {
		return new PxAccessParams(
			record.listOf(AccessParam.class), 
			this
		);
	}

	@Override
	public void update(String code, String name) throws IOException {
		
		record.isRequired(Access::name, name);	
		record.isRequired(Access::code, code);
		
		record.mustBeUnique(Access::code, code.toUpperCase());
		
		record.entryOf(Access::name, name)
			  .entryOf(Access::code, code.toUpperCase())
			  .update();
	}

	@Override
	public String module() throws IOException {
		return record.valueOf(Access::module);
	}

	@Override
	public int intParamValueOf(String code) throws IOException {
		return Integer.parseInt(paramValueOf(code));
	}

	@Override
	public String paramValueOf(String code) throws IOException {
		return parameters().where(AccessParam::code, code).first().defaultValue();
	}
}
