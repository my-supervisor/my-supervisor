package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgInteger;

public final class PgIntegerField extends PgFieldMetadata {

	public PgIntegerField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgInteger(), label);
	}
}
