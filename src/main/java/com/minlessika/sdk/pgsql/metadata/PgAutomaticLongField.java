package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.pgsql.type.PgAutomaticLong;

public final class PgAutomaticLongField extends PgFieldMetadata {

	public PgAutomaticLongField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgAutomaticLong(), label);
	}
}
