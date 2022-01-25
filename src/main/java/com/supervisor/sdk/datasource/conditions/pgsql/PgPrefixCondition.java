package com.supervisor.sdk.datasource.conditions.pgsql;

import com.supervisor.sdk.datasource.comparators.Matcher;
import com.supervisor.sdk.datasource.conditions.ConditionWrap;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;
import com.supervisor.sdk.pgsql.metadata.PgFieldOfMethod;

import java.io.IOException;

public final class PgPrefixCondition extends ConditionWrap {
	
	public <A1> PgPrefixCondition(final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef, final Matcher matcher) throws IOException {
		this(new PgFieldOfMethod(clazz, methodRef), matcher);
	}
	
	public PgPrefixCondition(final FieldMetadata field, final Matcher matcher) throws IOException {
		super(
				new PgSimpleCondition(
						String.format("%s.%s", field.entityName(), field.name()), 
						field, 
						matcher
				)
		);
	}
}
