package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgDateTime;

public final class PgDateTimeField extends PgFieldMetadata {

	public PgDateTimeField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgDateTime(), label);
	}
}
