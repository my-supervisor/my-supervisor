package com.supervisor.sdk.pgsql;

import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.pgsql.metadata.PgFieldOfMethod;
import com.supervisor.sdk.metadata.MethodReferenceUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PgSimpleOrdering implements Ordering {

	private final OrderDirection direction;
	private final List<String> fields;
	private final String alias;
	
	public PgSimpleOrdering(final String alias, final OrderDirection direction, List<String> fields) {
		
		if(fields.isEmpty())
			throw new IllegalArgumentException("Vous devez fournir au moins un argument pour tri de la requête !");
		
		this.direction = direction;
		this.fields = fields;
		this.alias = alias;
	}
	
	public PgSimpleOrdering(final String alias, final OrderDirection direction, FieldMetadata...fields) throws IOException {
		this(alias, direction, fields(Arrays.asList(fields)));
	}
	
	@SafeVarargs
	public <T> PgSimpleOrdering(final String alias, final OrderDirection direction, Class<T> clazz, MethodReferenceUtils.MethodRefWithoutArg<T>... methodRef) throws IOException {
		this(alias, direction, fields(clazz, methodRef));
	}
	
	public PgSimpleOrdering(final OrderDirection direction, FieldMetadata...fields) throws IOException {
		this(StringUtils.EMPTY, direction, fields(Arrays.asList(fields)));
	}
	
	@SafeVarargs
	public <T> PgSimpleOrdering(final OrderDirection direction, Class<T> clazz, MethodReferenceUtils.MethodRefWithoutArg<T>... methodRef) throws IOException {
		this(StringUtils.EMPTY, direction, fields(clazz, methodRef));
	}
	
	@SafeVarargs
	private static <T> List<String> fields(Class<T> clazz, MethodReferenceUtils.MethodRefWithoutArg<T>... methodRef) throws IOException{
		List<String> fields = new ArrayList<>();
		for (MethodReferenceUtils.MethodRefWithoutArg<T> m : methodRef) {
			fields.add(new PgFieldOfMethod(clazz, m).name());
		}
		
		return fields;
	}

	private static List<String> fields(List<FieldMetadata> fields) {
		List<String> names = new ArrayList<>();
		for (FieldMetadata m : fields) {
			names.add(m.name());
		}
		
		return names;
	}
	
	@Override
	public String script() {
		String script = StringUtils.EMPTY;
		
		String dir = "ASC";
		if(direction == OrderDirection.DESC)
			dir = "DESC";
		
		for (String f : fields) {
			if(StringUtils.isBlank(script)) {
				if(StringUtils.isBlank(alias))
					script = String.format("order by %s %s", f, dir);
				else
					script = String.format("order by %s.%s %s", alias, f, dir);
			}else {
				if(StringUtils.isBlank(alias))
					script = String.format("%s, %s %s", script, f, dir);
				else
					script = String.format("%s, %s.%s %s", script, alias, f, dir);
			}				
		}

		return script;
	}

	@Override
	public Ordering reverse() {
		
		OrderDirection reverseDirection = OrderDirection.ASC;
		if(direction == OrderDirection.ASC)
			reverseDirection = OrderDirection.DESC;
		
		return new PgSimpleOrdering(alias, reverseDirection, fields);
	}

}
