package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgUuid;

public final class PgUuidField extends PgFieldMetadata {

	public PgUuidField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgUuid(), label);
	}
}
