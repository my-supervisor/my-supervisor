package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgSmallint;

public final class PgSmallintField extends PgFieldMetadata {

	public PgSmallintField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgSmallint(), label);
	}
}
