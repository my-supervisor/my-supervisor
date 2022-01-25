package com.supervisor.sdk.pgsql.metadata;

import com.supervisor.sdk.pgsql.type.PgAutomaticLong;

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
