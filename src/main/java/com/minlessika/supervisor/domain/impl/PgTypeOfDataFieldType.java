package com.minlessika.supervisor.domain.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.minlessika.sdk.metadata.BaseType;
import com.minlessika.sdk.pgsql.type.PgBoolean;
import com.minlessika.sdk.pgsql.type.PgDate;
import com.minlessika.sdk.pgsql.type.PgDouble;
import com.minlessika.sdk.pgsql.type.PgString;
import com.minlessika.supervisor.domain.DataFieldType;

public final class PgTypeOfDataFieldType implements BaseType {

	private final BaseType origin;
	
	public PgTypeOfDataFieldType(final DataFieldType type) {
		
		final BaseType bType;
		
		switch (type) {
			case BOOLEAN:
				bType = new PgBoolean();
				break;
			case DATE:
				bType = new PgDate();
				break;
			case NUMBER:
				bType = new PgDouble();
				break;
			default:
				bType = new PgString();
				break;
		}		
		
		origin = bType;
	}

	@Override
	public String name() {
		return origin.name();
	}

	@Override
	public Type type() {
		return origin.type();
	}

	@Override
	public List<Type> similarTypes() {
		return origin.similarTypes();
	}

	@Override
	public boolean belongTo(Type type) {
		return origin.belongTo(type);
	}

	@Override
	public Object cast(String value) {
		return origin.cast(value);
	}
}
