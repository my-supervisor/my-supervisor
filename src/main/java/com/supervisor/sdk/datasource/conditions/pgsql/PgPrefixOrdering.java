package com.supervisor.sdk.datasource.conditions.pgsql;

import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;
import com.supervisor.sdk.pgsql.OrderingWrap;
import com.supervisor.sdk.pgsql.PgSimpleOrdering;
import com.supervisor.sdk.pgsql.metadata.PgFieldOfMethod;

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
