package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgDouble;

public final class PgDoubleField extends PgFieldMetadata {

	public PgDoubleField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgDouble(), label);
	}
}
