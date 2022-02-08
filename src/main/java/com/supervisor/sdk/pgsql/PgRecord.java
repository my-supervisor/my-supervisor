package com.supervisor.sdk.pgsql;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BaseScheme;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.datasource.ResultStatement;
import com.supervisor.sdk.datasource.Statement;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.MethodReferenceUtils;
import com.supervisor.sdk.pgsql.statement.PgSimpleSelectStatement;
import com.supervisor.sdk.pgsql.statement.PgSimpleUpdateStatement;
import com.supervisor.sdk.validations.IsRequired;
import com.supervisor.sdk.validations.MustBeUnique;
import com.supervisor.sdk.validations.MustCheckThisCondition;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PgRecord<A1 extends Recordable> implements Record<A1> {
	
	final UUID id;
	final Map<String, Object> entries;
	final Class<A1> clazz;
	final BaseScheme scheme;
	final Base base;
	
	public PgRecord(final Base base, final BaseScheme scheme, final Class<A1> clazz, final UUID id) {
		this(base, scheme, clazz, id, new HashMap<String, Object>());
	}
	
	public PgRecord(final Base base, final BaseScheme scheme, final Class<A1> clazz, final UUID id, final Map<String, Object> entries) {
		this.id = id;
		this.entries = entries;
		this.scheme = scheme;
		this.clazz = clazz;
		this.base = base;
	}
	
	@Override
	public UUID id() {
		return id;
	}
	
	@Override
	public String name() throws IOException {
		return scheme.nameOf(clazz);
	}
	
	@Override
	public LocalDateTime creationDate() throws IOException {
		java.sql.Timestamp date = valueOf("creation_date");
		return date.toLocalDateTime();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		java.sql.Timestamp date = valueOf("last_modification_date");
		return date.toLocalDateTime();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return valueOf("last_modifier_id");
	}

	@Override
	public UUID ownerId() throws IOException {
		return valueOf("owner_id");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T valueOf(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef) throws IOException {
		FieldMetadata field = scheme.fieldOf(clazz, methodRef);		
		Object value = PgRecordSet.convertSqlValueToJavaSupportedType(
							clazz, 
							methodRef, 
							valueOf(field)
					   ); 
		return (T) value;
	}

	@Override
	public <T> T valueOf(FieldMetadata field) throws IOException {
		return valueOf(field.name());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T valueOf(String field) throws IOException {
		Statement statement = new PgSimpleSelectStatement(base, Arrays.asList(field), new TableImpl(name()), "id=?", Arrays.asList(id));
    	List<ResultStatement> results = statement.execute();
    	
    	if(results.isEmpty())
    		throw new IllegalArgumentException(String.format("La valeur de la propriété %s de l'objet %s n'a pas été trouvée pour ID=%s !", field, name(), id));
    	
    	ResultStatement result = results.get(0);
    	return (T)result.data().get(field);
	}

	@Override
	public Record<A1> entryOf(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object value) throws IOException {
		
		FieldMetadata field = scheme.fieldOf(clazz, methodRef);
		Map<String, Object> newEntries = new HashMap<>();
		newEntries.putAll(entries);		
		
		Object valueConverted = PgRecordSet.convertValueInSupportedSqlType(clazz, methodRef, value);
		newEntries.put(field.name(), valueConverted);
		
		return new PgRecord<>(base, scheme, clazz, id, newEntries);
	}

	@Override
	public void update() throws IOException {
		if (entries.isEmpty())
			return;
		
		Statement statement = new PgSimpleUpdateStatement(base, name(), entries, id);
		statement.execute();
		
		entries.clear();
	}

	@Override
	public <T1 extends Recordable> Record<T1> of(Class<T1> clazz, UUID id) {
		return new PgRecord<>(base, scheme, clazz, id);
	}

	@Override
	public String tag() throws IOException {
		return valueOf("tag");
	}

	@Override
	public UUID creatorId() throws IOException {
		return valueOf("creator_id");
	}

	@Override
	public void mustBeUnique(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object newValue) throws IOException {
		mustBeUnique(methodRef, newValue, null);
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
	public void mustBeUnique(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object newValue, MethodReferenceUtils.MethodRefWithoutArg<A1> fieldRef)
			throws IOException {
		final String msg = String.format("%s : le champ %s existe déjà !", scheme.labelOf(clazz), scheme.labelOf(clazz, methodRef));
		final String oldValue = valueOf(methodRef);
		final Object refValue;
		if(fieldRef != null)
			refValue = valueOf(fieldRef);
		else
			refValue = null;
		
		new MustBeUnique<A1>(base.select(clazz), methodRef, oldValue, newValue, fieldRef, refValue, msg).validate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Recordable> Record<T> of(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef) throws IOException {
		UUID newId = valueOf(methodRef);
		Method m = MethodReferenceUtils.getReferencedMethod(clazz, methodRef);
		return of((Class<T>)m.getReturnType(), newId);
	}

	@Override
	public <T1 extends Recordable> RecordSet<T1> listOf(Class<T1> clazz) throws IOException {
		return base.select(clazz);
	}

	@Override
	public Base base() {
		return base;
	}

	@Override
	public RecordSet<A1> toList() throws IOException {
		return listOf(clazz);
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return base.select(clazz, viewScript);
	}
}
