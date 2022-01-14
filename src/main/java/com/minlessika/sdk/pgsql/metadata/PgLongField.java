package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgLong;

public final class PgLongField extends PgFieldMetadata {

	public PgLongField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgLong(), label);
	}
}
