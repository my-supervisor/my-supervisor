package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgBoolean;

public final class PgBooleanField extends PgFieldMetadata {

	public PgBooleanField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgBoolean(), label);
	}
}
