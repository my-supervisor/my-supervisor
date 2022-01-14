package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.FieldMetadataWrap;
import org.apache.commons.lang.StringUtils;

public final class PgPrimaryKeyField extends FieldMetadataWrap {

	public PgPrimaryKeyField(final FieldMetadata origin) {
		super(origin);
	}

	@Override
	public String definitionScript() {
		return String.format("%s %s NOT NULL", name(), type().name());
	}
	
	@Override
	public String constraintScript() {
		String constraint = String.format("CONSTRAINT %s_pkey PRIMARY KEY (%s)", entityName(), name());
		
		if(!StringUtils.isBlank(origin.constraintScript()))
			constraint = String.format("%s, %s", origin.constraintScript(), constraint);
		
		return constraint;
	}
}
