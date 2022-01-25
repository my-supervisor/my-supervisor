package com.supervisor.sdk.pgsql.metadata;

import com.supervisor.sdk.pgsql.type.PgUuid;

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
