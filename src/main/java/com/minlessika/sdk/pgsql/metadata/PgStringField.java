package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.metadata.BaseType;
import com.minlessika.sdk.pgsql.type.PgString;

public final class PgStringField extends PgFieldMetadata {

	public PgStringField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label
	) {
		super(order, entityName, name, new PgString(), label);
	}
	
	public PgStringField(
			final Integer order, 
			final String entityName, 
			final String name, 
			final String label, 
			BaseType type
	) {
		super(order, entityName, name, type, label);
	}
}
