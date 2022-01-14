package com.minlessika.sdk.datasource.conditions.pgsql;

import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;
import com.minlessika.sdk.pgsql.OrderingWrap;
import com.minlessika.sdk.pgsql.PgSimpleOrdering;
import com.minlessika.sdk.pgsql.metadata.PgFieldOfMethod;

import java.io.IOException;

public final class PgPrefixOrdering extends OrderingWrap {

	public <A1> PgPrefixOrdering(final OrderDirection direction, final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef) throws IOException {
		this(
			direction,
			new PgFieldOfMethod(clazz, methodRef)
		);
	}
	
	public PgPrefixOrdering(final OrderDirection direction, final FieldMetadata field) throws IOException {
		super(
			new PgSimpleOrdering(
				String.format("%s.%s", field.entityName(), field.name()), 
				direction, 
				field
			)
		);
	}

}
