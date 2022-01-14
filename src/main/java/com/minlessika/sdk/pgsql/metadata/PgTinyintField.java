package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgTinyint;

public final class PgTinyintField extends PgFieldMetadata {

	public PgTinyintField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgTinyint(), label);
	}
}
