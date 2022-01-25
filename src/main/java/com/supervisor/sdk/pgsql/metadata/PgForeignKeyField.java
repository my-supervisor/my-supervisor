package com.supervisor.sdk.pgsql.metadata;

import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.FieldMetadataWrap;
import org.apache.commons.lang.StringUtils;

public final class PgForeignKeyField extends FieldMetadataWrap {

	final FieldMetadata foreignField;
	
	public PgForeignKeyField(final FieldMetadata origin, final FieldMetadata foreignField) {
		super(origin);
		
		this.foreignField = foreignField;
	}

	@Override
	public String constraintScript() {
		String constraint = String.format("CONSTRAINT %s_%s_fkey FOREIGN KEY (%s) " + 
										  "REFERENCES %s (%s) MATCH SIMPLE " + 
										  "ON UPDATE NO ACTION ON DELETE CASCADE", entityName(), name(), name(), foreignField.entityName(), foreignField.name());
		
		if(!StringUtils.isBlank(origin.constraintScript()))
			constraint = String.format("%s, %s", origin.constraintScript(), constraint);
		
		return constraint;
	}
}
