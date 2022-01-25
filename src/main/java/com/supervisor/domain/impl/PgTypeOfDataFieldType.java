package com.supervisor.domain.impl;

import java.lang.reflect.Type;
import java.util.List;

import com.supervisor.sdk.metadata.BaseType;
import com.supervisor.sdk.pgsql.type.PgBoolean;
import com.supervisor.sdk.pgsql.type.PgDate;
import com.supervisor.sdk.pgsql.type.PgDouble;
import com.supervisor.sdk.pgsql.type.PgString;
import com.supervisor.domain.DataFieldType;

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
