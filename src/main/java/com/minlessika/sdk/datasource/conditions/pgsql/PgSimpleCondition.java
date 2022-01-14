package com.minlessika.sdk.datasource.conditions.pgsql;

import com.minlessika.sdk.datasource.comparators.Matcher;
import com.minlessika.sdk.datasource.comparators.pgsql.PgSmartMatcher;
import com.minlessika.sdk.datasource.conditions.BaseCondition;
import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;
import com.minlessika.sdk.pgsql.PgRecordSet;
import com.minlessika.sdk.pgsql.metadata.PgFieldOfMethod;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;

public final class PgSimpleCondition extends BaseCondition {

	private final String sqlType;
	private final String alias;
	private final Matcher matcher;
	
	public <A1> PgSimpleCondition(final String alias, final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef, final Matcher matcher, final String sqlType) throws IOException {
		this(alias, new PgFieldOfMethod(clazz, methodRef), matcher, sqlType);
	}
	
	public <A1> PgSimpleCondition(final String alias, final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef, final Matcher matcher) throws IOException {
		this(alias, new PgFieldOfMethod(clazz, methodRef), matcher, StringUtils.EMPTY);
	}
	
	public <A1> PgSimpleCondition(final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef, final Matcher matcher) throws IOException {
		this(new PgFieldOfMethod(clazz, methodRef), matcher);
	}
	
	public <A1> PgSimpleCondition(final Class<A1> clazz, final MethodRefWithoutArg<A1> methodRef, final Matcher matcher, final String sqlType) throws IOException {
		this(new PgFieldOfMethod(clazz, methodRef).name(), new PgFieldOfMethod(clazz, methodRef), matcher, sqlType);
	}
	
	public PgSimpleCondition(final FieldMetadata field, final Matcher matcher) throws IOException {
		this(field.name(), field, matcher, StringUtils.EMPTY);
	}
	
	public PgSimpleCondition(final String alias, final FieldMetadata field, final Matcher matcher) throws IOException {
		this(alias, field, matcher, StringUtils.EMPTY);
	}
	
	public PgSimpleCondition(final String alias, final FieldMetadata field, final Matcher matcher, final String sqlType) throws IOException {
		this.sqlType = sqlType;
		this.alias = alias;
		this.matcher = new PgSmartMatcher(field, matcher);
	}
	
	@Override
	public String toScritpt() throws IOException {
		if(StringUtils.isBlank(sqlType)) {
			return String.format(
					PgRecordSet.sqlClauseOf(
							alias, 
							matcher.operator()
					)
			  );
		}else {
			return String.format(
					PgRecordSet.sqlClauseOf(
							alias, 
							sqlType,
							matcher.operator()
					)
			  );
		}
		
	}

	@Override
	public List<Object> parameters() throws IOException {
		return matcher.operands();
	}

}
