package com.supervisor.sdk.pgsql;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BaseScheme;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.datasource.ResultStatement;
import com.supervisor.sdk.datasource.Statement;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.datasource.comparators.Matcher;
import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.datasource.conditions.Condition;
import com.supervisor.sdk.datasource.conditions.Filter;
import com.supervisor.sdk.datasource.conditions.pgsql.ConditionOperator;
import com.supervisor.sdk.datasource.conditions.pgsql.PgFilter;
import com.supervisor.sdk.datasource.conditions.pgsql.PgSimpleCondition;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.MethodReferenceUtils;
import com.supervisor.sdk.pgsql.statement.PgDeleteManyStatement;
import com.supervisor.sdk.pgsql.statement.PgInsertStatement;
import com.supervisor.sdk.pgsql.statement.PgSimpleSelectStatement;
import com.supervisor.sdk.validations.IsRequired;
import com.supervisor.sdk.validations.MustBeUnique;
import com.supervisor.sdk.validations.MustCheckThisCondition;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

public final class PgRecordSet<A1 extends Recordable> implements RecordSet<A1> {

	private final Class<A1> clazz;
	private final Filter<A1> filter;
	private final Map<String, Object> entries;
	private final BaseScheme scheme;
	private final Ordering ordering;
	private final Base base;
	private final Table table;
	private final Long limit;
	private final Long start;
	private static final String MAP_SEPARATOR = "$#:#:$";
	
	public PgRecordSet(final Base base, final BaseScheme scheme, final Class<A1> clazz) throws IOException {
		this(base, scheme, clazz, new TableImpl(clazz));
	}
	
	public PgRecordSet(final Base base, final BaseScheme scheme, final Class<A1> clazz, final Table table) throws IOException {
		this(base, scheme, clazz, table, new HashMap<String, Object>(), new PgFilter<>(clazz), Ordering.EMPTY, 0L, 0L);
	}
	
	public PgRecordSet(final Base base, final BaseScheme scheme, final Class<A1> clazz, final Table table, final Map<String, Object> entries, final Filter<A1> filter, final Ordering ordering, final Long limit, final Long start) {
		this.filter = filter.copy(); 
		this.clazz = clazz;
		this.entries = entries;
		this.scheme = scheme;
		this.base = base;
		this.table = table;	
		this.ordering = ordering;
		this.limit = limit;
		this.start = start;
	}

	@Override
	public List<Record<A1>> items() throws IOException {
		
		String clause = filter.condition().toScritpt();
		List<Object> parameters = filter.condition().parameters();
		
		Statement statement = new PgSimpleSelectStatement(base, Arrays.asList("id"), table, clause, parameters, ordering.script(), limit, start);
    	List<ResultStatement> results = statement.execute();
		
		List<Record<A1>> items = new ArrayList<>();
		for (ResultStatement result : results) {
			items.add(new PgRecord<A1>(base, scheme, clazz, (UUID)result.data().get("id")));
		}
		
		return items;
	}

	@Override
	public long count() throws IOException {
		String clause = filter.condition().toScritpt();
		List<Object> parameters = filter.condition().parameters();
		
		Statement statement = new PgSimpleSelectStatement(base, Arrays.asList("COUNT(*) AS nb"), table, clause, parameters);
		List<ResultStatement> results = statement.execute();
		
		return (Long)results.get(0).data().get("nb");
	}

	@Override
	public boolean isEmpty() throws IOException {
		return count() == 0;
	}

	@Override
	public Record<A1> first() throws IOException {
		
		if(isEmpty())
			throw new IllegalArgumentException("La ressource demandée n'est pas disponible !");
		
		String clause = filter.condition().toScritpt();
		List<Object> parameters = filter.condition().parameters();
		
		Statement statement = new PgSimpleSelectStatement(base, Arrays.asList("id"), table, clause, parameters, ordering.script(), 1L, 1L);
		List<ResultStatement> results = statement.execute();
		
		return new PgRecord<>(base, scheme, clazz, (UUID)results.get(0).data().get("id"));
	}

	@Override
	public Record<A1> last() throws IOException {
		if(isEmpty())
			throw new IllegalArgumentException("La ressource demandée n'est pas disponible !");
		
		String clause = filter.condition().toScritpt();
		List<Object> parameters = filter.condition().parameters();
		
		Statement statement = new PgSimpleSelectStatement(base, Arrays.asList("id"), table, clause, parameters, ordering.reverse().script(), 1L, 1L);
		List<ResultStatement> results = statement.execute();
		
		return new PgRecord<>(base, scheme, clazz, (UUID)results.get(0).data().get("id"));
	}

	@Override
	public void remove() throws IOException {
		
		String scriptCondition = filter.condition().toScritpt();
		
		if(table.name().contains(") as ") || table.name().contains("src.*")) {
			if(StringUtils.isBlank(scriptCondition))
				scriptCondition = String.format("id in (select id from %s)", table.name());
			else
				scriptCondition = String.format("(%s) and id in (select id from %s)", scriptCondition, table.name());
		}
				 
		final Statement statement = new PgDeleteManyStatement(base, scheme.nameOf(clazz), scriptCondition, filter.condition().parameters());
    	statement.execute();
	}

	@Override
	public RecordSet<A1> entryOf(FieldMetadata field, Object value) throws IOException {
		Map<String, Object> newEntries = new HashMap<>();
		newEntries.putAll(entries);
				
		newEntries.put(field.name(), value);
		
		return new PgRecordSet<>(base, scheme, clazz, table, newEntries, filter, ordering, limit, start);
	}
	
	@Override
	public RecordSet<A1> entryOf(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object value) throws IOException {
		
		FieldMetadata field = scheme.fieldOf(clazz, methodRef);		
		Object valueConverted = convertValueInSupportedSqlType(clazz, methodRef, value);
		
		return entryOf(field, valueConverted);
	}

	@Override
	public Record<A1> add() throws IOException {
		return addForUser(base.currentUserId());
	}

	@Override
	public RecordSet<A1> where(final Condition left) throws IOException {
		
		final Filter<A1> newFilter = filter.copy();
		
		return new PgRecordSet<>(
				base, 
				scheme, 				
				clazz, 
				table,
				entries,
				newFilter.add(left),
				ordering,
				limit,
				start
		);
	}

	@Override
	public boolean any() throws IOException {
		return !isEmpty();
	}

	@Override
	public RecordSet<A1> where(FieldMetadata field, Object value) throws IOException {
		return where(field, Matchers.equalsTo(value));
	}

	@Override
	public <T1 extends Recordable> RecordSet<T1> of(Class<T1> clazz) throws IOException {
		return new PgRecordSet<>(
				base, 
				scheme,  
				clazz
		);
	}

	@Override
	public RecordSet<A1> where(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object value) throws IOException {
		return where(methodRef, Matchers.equalsTo(value));	
	}
	
	@Override
	public void mustBeUnique(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object newValue) throws IOException {
		mustBeUnique(methodRef, newValue, null, null); 
	}

	@Override
	public void isRequired(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, String value) throws IOException {
		String msg = String.format("%s : le champ %s doit être renseigné !", scheme.labelOf(clazz), scheme.labelOf(clazz, methodRef));
		new IsRequired(value, msg).validate();
	}

	@Override
	public void mustCheckThisCondition(boolean condition, String msg) throws IOException {
		new MustCheckThisCondition(condition, msg).validate();
	}

	@Override
	public void mustBeUnique(final MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, final Object newValue, MethodReferenceUtils.MethodRefWithoutArg<A1> fieldRef, Object refValue)
			throws IOException {
		String msg = String.format("%s : le champ %s existe déjà !", scheme.labelOf(clazz), scheme.labelOf(clazz, methodRef));
		new MustBeUnique<A1>(this, methodRef, newValue, fieldRef, refValue, msg).validate();
	}

	@Override
	public RecordSet<A1> where(final FieldMetadata field, final Matcher matcher) throws IOException {
		return where(new PgSimpleCondition(field, matcher));
	}

	@Override
	public RecordSet<A1> where(final MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, final Matcher matcher) throws IOException {
		
		return where(
				new PgSimpleCondition(
						clazz,
						methodRef, 
					    matcher
				)
		);	
	}
	
	public static <A1> List<Object> convertValuesInSupportedSqlTypes(final Class<A1> clazz, final MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, List<Object> values) throws IOException{
		
		Method m = MethodReferenceUtils.getReferencedMethod(clazz, methodRef);
		return convertValuesInSupportedSqlTypes(m.getReturnType(), values);
	}

	public static <A1> Object convertValueInSupportedSqlType(final Class<A1> clazz, final MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object value) throws IOException{
		
		Method m = MethodReferenceUtils.getReferencedMethod(clazz, methodRef);
		return convertValuesInSupportedSqlTypes(m.getReturnType(), Arrays.asList(value)).get(0);
	}
	
	public static List<Object> convertValuesInSupportedSqlTypes(Class<?> clazz, List<Object> values) throws IOException{

		List<Object> valuesConverted = new ArrayList<>();
		
		for (int i = 0; i < values.size(); i++) {
			Object value = values.get(i);
			if(value == null) {
				valuesConverted.add(null);
				continue;
			}
				
			if(clazz.isEnum()) {
				Method mName;
				try {
					
					final Object name;
					if(value instanceof String) {
						name = value; // already converted
					} else {
						mName = clazz.getMethod("name");
						name = mName.invoke(value, new Object[] {});
					}
					
					valuesConverted.add(name);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					throw new IOException(e);
				}				
			} else if(clazz == List.class) { // liste de string				
				
				if(value instanceof List) {
					@SuppressWarnings("unchecked")
					List<String> list = (List<String>)value;
					if(list.isEmpty())
						valuesConverted.add("");
					else
						valuesConverted.add(StringUtils.join(list, "|"));
				} else {
					valuesConverted.add(value);
				}
				
			} else if(clazz == Map.class) { // map de string				
				
				if(value instanceof Map) {
					@SuppressWarnings("unchecked")
					Map<String, String> map = (Map<String, String>)value;
					if(map.isEmpty())
						valuesConverted.add("");
					else {
						List<String> list = new ArrayList<>();
						for (Entry<String, String> entry : map.entrySet()) {
							list.add(String.format("%s%s%s", entry.getKey(), MAP_SEPARATOR, entry.getValue()));
						}
						
						valuesConverted.add(StringUtils.join(list, "|"));
					}
				} else {
					valuesConverted.add(value);
				}
				
			} else if(clazz == LocalDate.class) {				
				valuesConverted.add(java.sql.Date.valueOf((LocalDate)value));
			} else if(clazz == LocalDateTime.class) {
				valuesConverted.add(java.sql.Timestamp.valueOf((LocalDateTime)value));
			} else {
				valuesConverted.add(value);
			}
		}
		
		return valuesConverted;
	}
	
	public static <A1> Object convertSqlValueToJavaSupportedType(Class<A1> clazz, MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object value) throws IOException{

		Method m = MethodReferenceUtils.getReferencedMethod(clazz, methodRef);		
		return convertSqlValueToJavaSupportedType(m.getReturnType(), value);
	}
	
	public static Object convertSqlValueToJavaSupportedType(Class<?> clazz, Object value) throws IOException{

		Object valueConverted;
		
		try {
			if(clazz.isEnum()) { // vérifier si c'est un enum
				Method mValueOf = clazz.getDeclaredMethod("valueOf", String.class);
				valueConverted = mValueOf.invoke(null, value);
			} else if(clazz == List.class) {
				if(value == null)
					valueConverted = new ArrayList<>();
				else
					valueConverted = new ArrayList<>(Arrays.asList(StringUtils.split((String)value, '|')));
			} else if(clazz == Map.class) {
				final Map<String, String> map = new HashMap<>();
				if(value != null) {
					List<String> list = Arrays.asList(StringUtils.split((String)value, '|'));
					for (String string : list) {
						String[] data = StringUtils.split(string, MAP_SEPARATOR);
						map.put(data[0], data[1]);
					}
				}
				
				valueConverted = map;
			} else if(clazz == LocalDate.class) {
				if(value == null) {
					valueConverted = null;
				} else {
					java.sql.Date date = (java.sql.Date)value;
					valueConverted = date.toLocalDate();
				}				
			}else if(clazz == LocalDateTime.class) {
				if(value == null) {
					valueConverted = null;
				} else {
					java.sql.Timestamp date = (java.sql.Timestamp)value;
					valueConverted = date.toLocalDateTime();
				}				
			} else {
				valueConverted = value;
			}				
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
				
		return valueConverted;
	}
	
	public static String sqlClauseOf(String leftField, Comparator operator, String rightField) {
		String clause;
		
		switch (operator) {
			case EQUALS:
				clause = String.format("%s=%s", leftField, rightField);
				break;
			case NOT_EQUALS:
				clause = String.format("%s<>%s", leftField, rightField);
				break;
			case GREATER_THAN:
				clause = String.format("%s>%s", leftField, rightField);
				break;
			case LESS_THAN:
				clause = String.format("%s<%s", leftField, rightField);
				break;
			case GREATER_OR_EQUALS:
				clause = String.format("%s>=%s", leftField, rightField);
				break;
			case LESS_OR_EQUALS:
				clause = String.format("%s<=%s", leftField, rightField);
				break;
			case CONTAINS:
				clause = String.format("%s LIKE %s", leftField, "concat('%'::character varying, " + rightField + ", '%'::character varying)");
				break;
			case NOT_CONTAINS:
				clause = String.format("NOT(%s LIKE %s)", leftField, "concat('%'::character varying, " + rightField + ", '%'::character varying)");
				break;
			case STARTS_WITH:
				clause = String.format("%s LIKE %s", leftField, "concat(" + rightField + ", '%'::character varying)");
				break;
			case NOT_STARTS_WITH:
				clause = String.format("NOT(%s LIKE %s)", leftField, "concat(" + rightField + ", '%'::character varying)");
				break;
			case ENDS_WITH:
				clause = String.format("%s LIKE %s", leftField, "concat('%'::character varying, " + rightField + ")");
				break;
			case NOT_ENDS_WITH:
				clause = String.format("NOT(%s LIKE %s)", leftField, "concat('%'::character varying, " + rightField + ")");
				break;
			case IS_PRESENT:
				clause = String.format("%s IS NOT NULL", leftField);
				break;
			case IS_ABSENT:
				clause = String.format("%s IS NULL", leftField);
				break;
			default:
				throw new IllegalArgumentException("L'opérateur de comparaison n'est pas pris en charge !"); 
		}
		
		return clause;
	}
	
	public static String sqlClauseOf(String field, Comparator operator) {
		String clause;
		
		switch (operator) {
			case EQUALS:
				clause = String.format("%s=?", field);
				break;
			case IS_PRESENT:
				clause = String.format("%s IS NOT NULL", field);
				break;
			case NOT_EQUALS:
				clause = String.format("%s<>?", field);
				break;
			case IS_ABSENT:
				clause = String.format("%s IS NULL", field);
				break;
			case GREATER_THAN:
				clause = String.format("%s>?", field);
				break;
			case LESS_THAN:
				clause = String.format("%s<?", field);
				break;
			case GREATER_OR_EQUALS:
				clause = String.format("%s>=?", field);
				break;
			case LESS_OR_EQUALS:
				clause = String.format("%s<=?", field);
				break;
			case BETWEEN:
				clause = String.format("%s BETWEEN ? AND ?", field);
				break;
			case NOT_BETWEEN:
				clause = String.format("%s NOT BETWEEN ? AND ?", field);
				break;
			case CONTAINS:
				clause = String.format("%s LIKE ?", field);
				break;
			case NOT_CONTAINS:
				clause = String.format("NOT(%s LIKE ?)", field);
				break;
			case STARTS_WITH:
				clause = String.format("%s LIKE ?", field);
				break;
			case NOT_STARTS_WITH:
				clause = String.format("NOT(%s LIKE ?)", field);
				break;
			case ENDS_WITH:
				clause = String.format("%s LIKE ?", field);
				break;
			case NOT_ENDS_WITH:
				clause = String.format("NOT(%s LIKE ?)", field);
				break;
			default:
				throw new IllegalArgumentException("L'opérateur de comparaison n'est pas défini !"); 
		}
		
		return clause;
	}
	
	public static String sqlClauseOf(String field, String sqlType, Comparator operator) {
		String clause;
		
		if(sqlType.equals("bigserial"))
			sqlType = "bigint";
		
		switch (operator) {
			case EQUALS:
				clause = String.format("%s::%s=?::%s", field, sqlType, sqlType);
				break;
			case IS_PRESENT:
				clause = String.format("%s::%s IS NOT NULL", field, sqlType);
				break;
			case NOT_EQUALS:
				clause = String.format("%s::%s<>?::%s", field, sqlType, sqlType);
				break;
			case IS_ABSENT:
				clause = String.format("%s::%s IS NULL", field, sqlType);
				break;
			case GREATER_THAN:
				clause = String.format("%s::%s>?::%s", field, sqlType, sqlType);
				break;
			case LESS_THAN:
				clause = String.format("%s::%s<?::%s", field, sqlType, sqlType);
				break;
			case GREATER_OR_EQUALS:
				clause = String.format("%s::%s>=?::%s", field, sqlType, sqlType);
				break;
			case LESS_OR_EQUALS:
				clause = String.format("%s::%s<=?::%s", field, sqlType, sqlType);
				break;
			case BETWEEN:
				clause = String.format("%s::%s BETWEEN ?::%s AND ?::%s", field, sqlType, sqlType, sqlType);
				break;
			case NOT_BETWEEN:
				clause = String.format("%s::%s NOT BETWEEN ?::%s AND ?::%s", field, sqlType, sqlType, sqlType);
				break;
			case CONTAINS:
				clause = String.format("%s::%s LIKE ?::%s", field, sqlType, sqlType);
				break;
			case NOT_CONTAINS:
				clause = String.format("NOT(%s::%s LIKE ?::%s)", field, sqlType, sqlType);
				break;
			case STARTS_WITH:
				clause = String.format("%s::%s LIKE ?::%s", field, sqlType, sqlType);
				break;
			case NOT_STARTS_WITH:
				clause = String.format("NOT(%s::%s LIKE ?::%s)", field, sqlType, sqlType);
				break;
			case ENDS_WITH:
				clause = String.format("%s::%s LIKE ?::%s", field, sqlType, sqlType);
				break;
			case NOT_ENDS_WITH:
				clause = String.format("NOT(%s::%s LIKE ?::%s)", field, sqlType, sqlType);
				break;
			default:
				throw new IllegalArgumentException("L'opérateur de comparaison n'est pas défini !"); 
		}
		
		return clause;
	}

	@Override
	public Filter<A1> orFilter() {
		return new PgFilter<>(clazz, ConditionOperator.OR);
	}
	
	@Override
	public Filter<A1> andFilter() {
		return new PgFilter<>(clazz, ConditionOperator.AND);
	}

	@Override
	public RecordSet<A1> where(Filter<A1> filter) throws IOException {
		return where(filter.condition());
	}

	@Override
	public RecordSet<A1> orderBy(FieldMetadata field, OrderDirection direction) throws IOException {
		return new PgRecordSet<>(
				base, 
				scheme,  
				clazz, 
				table,
				entries,
				filter,
				new PgSimpleOrdering(direction, field), 
				limit,
				start
		);
	}

	@Override
	public RecordSet<A1> orderBy(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, OrderDirection direction) throws IOException {
		return new PgRecordSet<>(
				base, 
				scheme, 
				clazz, 
				table,
				entries,
				filter,
				new PgSimpleOrdering(direction, clazz, methodRef), 
				limit,
				start
		);
	}

	@Override
	public RecordSet<A1> orderBy(Ordering ordering) throws IOException {
		return new PgRecordSet<>(
				base, 
				scheme, 
				clazz, 
				table,
				entries,
				filter,
				ordering, 
				limit,
				start
		);
	}

	@Override
	public RecordSet<A1> orderBy(FieldMetadata field) throws IOException {
		return orderBy(field, OrderDirection.ASC);
	}

	@Override
	public RecordSet<A1> orderBy(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef) throws IOException {
		return orderBy(methodRef, OrderDirection.ASC);
	}

	@Override
	public RecordSet<A1> orderBy(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef1, MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef2,
                                 OrderDirection direction) throws IOException {
		return new PgRecordSet<>(
				base, 
				scheme, 
				clazz, 
				table,
				entries,
				filter,
				new PgSimpleOrdering(direction, clazz, methodRef1, methodRef2),
				limit,
				start
		);
	}

	@Override
	public RecordSet<A1> orderBy(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef1, MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef2)
			throws IOException {
		return new PgRecordSet<>(
				base, 
				scheme,  
				clazz, 
				table,
				entries,
				filter,
				new PgSimpleOrdering(OrderDirection.ASC, clazz, methodRef1, methodRef2), 
				limit,
				start
		);
	}

	@Override
	public RecordSet<A1> limit(Long number) throws IOException {
		return new PgRecordSet<>(
				base, 
				scheme, 
				clazz, 
				table,
				entries,
				filter,
				ordering, 
				number,
				start
		);
	}

	@Override
	public RecordSet<A1> start(Long position) throws IOException {
		return new PgRecordSet<>(
				base, 
				scheme, 
				clazz, 
				table,
				entries,
				filter,
				ordering, 
				limit,
				position
		);
	}

	@Override
	public Record<A1> get(UUID id) throws IOException {
		return where(A1::id, id).first();
	}

	@Override
	public Optional<Record<A1>> getOrDefault(UUID id) throws IOException {
		Record<A1> item = null;
		
		try {
			item = get(id);
		} catch (Exception e) {

		}
		
		return Optional.of(item);
	}

	@Override
	public void remove(UUID id) throws IOException {
		where(A1::id, id).remove();
	}

	@Override
	public void remove(Record<A1> item) throws IOException {
		remove(item.id());
	}

	@Override
	public boolean contains(UUID id) throws IOException {
		return where(A1::id, id).any();
	}

	@Override
	public boolean contains(Record<A1> item) throws IOException {
		return contains(item.id());
	}

	@Override
	public <T1 extends Recordable> RecordSet<T1> of(Class<T1> clazz, String viewScript) throws IOException {
		return base.select(clazz, viewScript);
	}

	@Override
	public Base base() {
		return base;
	}

	@Override
	public Object aggregate(String aggScript, String alias) throws IOException {
		
		String clause = filter.condition().toScritpt();
		List<Object> parameters = filter.condition().parameters();
		
		Statement statement = new PgSimpleSelectStatement(base, Arrays.asList(String.format("%s as %s", aggScript, alias)), table, clause, parameters);
		List<ResultStatement> results = statement.execute();
		
		return results.get(0).data().get(alias);
	}

	@Override
	public Record<A1> addForUser(UUID uid) throws IOException {
		entries.put("owner_id", uid);
		Statement statement = new PgInsertStatement(base, scheme.nameOf(clazz), entries);
		List<ResultStatement> results = statement.execute();
		
		entries.clear();
		
		return new PgRecord<>(base, scheme, clazz, (UUID)results.get(0).data().get("id"));
	}

	@Override
	public Filter<A1> filter() {
		return filter;
	}
}
