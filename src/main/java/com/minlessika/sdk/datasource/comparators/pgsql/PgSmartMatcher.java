package com.minlessika.sdk.datasource.comparators.pgsql;

import com.minlessika.sdk.datasource.comparators.Matcher;
import com.minlessika.sdk.datasource.comparators.MatcherWrap;
import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;
import com.minlessika.sdk.pgsql.PgRecordSet;
import com.minlessika.sdk.pgsql.metadata.PgFieldOfMethod;

import java.io.IOException;
import java.util.List;

public class PgSmartMatcher extends MatcherWrap {
	
	public PgSmartMatcher(final FieldMetadata field, Matcher origin) throws IOException {
		super(origin.withOperands(convertOperands(field, origin)));
	}
	
	public <A1> PgSmartMatcher(final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef, Matcher origin) throws IOException {
		super(origin.withOperands(convertOperands(clazz, methodRef, origin)));
	}

	static private <A1> List<Object> convertOperands(final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef, Matcher matcher) throws IOException{
		FieldMetadata field = new PgFieldOfMethod(clazz, methodRef);
		return convertOperands(field, matcher);
	}
	
	static private <A1> List<Object> convertOperands(final FieldMetadata field, Matcher matcher) throws IOException{
		return PgRecordSet.convertValuesInSupportedSqlTypes((Class<?>)field.type().type(), matcher.operands());
	}
}
