package com.minlessika.sdk.datasource;

import com.minlessika.sdk.pgsql.PgBaseScheme;

public final class TableImpl implements Table {

	private final String name;
	
	public <A1> TableImpl(final Class<A1> clazz) {
		this(PgBaseScheme.nameOfClazz(clazz));
	}
	
	public TableImpl(final String name) {
		this.name = name;
	}
	
	@Override
	public String name() {
		return name;
	}

}
