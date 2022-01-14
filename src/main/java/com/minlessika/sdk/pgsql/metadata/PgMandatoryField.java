package com.minlessika.sdk.pgsql.metadata;

import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.FieldMetadataWrap;

public final class PgMandatoryField extends FieldMetadataWrap {

	public PgMandatoryField(final FieldMetadata origin) {
		super(origin);
	}

	@Override
	public String definitionScript() {
		return String.format("%s %s NOT NULL", name(), type().name());
	}
}
