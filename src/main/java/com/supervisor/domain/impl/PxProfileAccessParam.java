package com.supervisor.domain.impl;

import com.supervisor.domain.AccessParam;
import com.supervisor.domain.AccessParamQuantifier;
import com.supervisor.domain.AccessParamValueType;
import com.supervisor.domain.Profile;
import com.supervisor.domain.ProfileAccess;
import com.supervisor.domain.ProfileAccessParam;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxProfileAccessParam extends DomainRecordable<ProfileAccessParam> implements ProfileAccessParam {
	
	public PxProfileAccessParam(final Record<ProfileAccessParam> source) throws IOException {
		super(source);
	}

	@Override
	public String code() throws IOException {
		return paramInherited().code();
	}

	@Override
	public String name() throws IOException {
		return paramInherited().name();
	}

	@Override
	public String defaultValue() throws IOException {
		return paramInherited().defaultValue();
	}

	@Override
	public void update(String code, String name, String defaultValue, AccessParamQuantifier quantifier, String message) throws IOException {
		paramInherited().update(code, name, defaultValue, quantifier, message);
	}

	@Override
	public Profile profile() throws IOException {
		return access().profile();
	}

	@Override
	public ProfileAccess access() throws IOException {
		Record<ProfileAccess> rec = record.of(ProfileAccessParam::access);
		return new PxProfileAccess(rec);
	}

	@Override
	public AccessParam paramInherited() throws IOException {
		Record<AccessParam> rec = record.of(ProfileAccessParam::paramInherited);
		return new PxAccessParam(rec);
	}

	@Override
	public String value() throws IOException {
		return record.valueOf(ProfileAccessParam::value);
	}

	@Override
	public void update(String value) throws IOException {
		record.isRequired(ProfileAccessParam::value, value);
		
		if(!isValid(value))
			throw new IllegalArgumentException("La type de la valeur ne correspond pas !");
		
		record.entryOf(ProfileAccessParam::value, value)
		      .update();
	}

	@Override
	public AccessParamValueType valueType() throws IOException {
		return paramInherited().valueType();
	}

	@Override
	public boolean isValid(String value) throws IOException {
		return paramInherited().isValid(value);
	}

	@Override
	public AccessParamQuantifier quantifier() throws IOException {
		return paramInherited().quantifier();
	}

	@Override
	public String message() throws IOException {
		return paramInherited().message();
	}
}
