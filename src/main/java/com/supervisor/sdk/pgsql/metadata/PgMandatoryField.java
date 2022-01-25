package com.supervisor.sdk.pgsql.metadata;

import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.FieldMetadataWrap;

public final class PgMandatoryField extends FieldMetadataWrap {

	public PgMandatoryField(final FieldMetadata origin) {
		super(origin);
	}

	@Override
	public String definitionScript() {
		return String.format("%s %s NOT NULL", name(), type().name());
	}
}
