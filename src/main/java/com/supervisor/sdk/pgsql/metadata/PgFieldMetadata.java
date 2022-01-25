package com.supervisor.sdk.pgsql.metadata;

import com.supervisor.sdk.metadata.BaseType;
import com.supervisor.sdk.metadata.FieldMetadata;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.takes.misc.EntryImpl;

import java.lang.reflect.Type;
import java.util.Map.Entry;

public class PgFieldMetadata implements FieldMetadata {

	final String entityName;
	final String name;
	final BaseType type;
	final String label; 
	final Integer order;
	
	public PgFieldMetadata(final Integer order, final String entityName, final String name, final BaseType type, final String label) {
		this.order = order;
		this.name = name;
		this.type = type;
		this.label = label;
		this.entityName = entityName;
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public BaseType type() {
		return type;
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public String definitionScript() {
		return String.format("%s %s", name(), type().name());
	}

	@Override
	public String constraintScript() {
		return StringUtils.EMPTY;
	}

	@Override
	public String commentScript() {
		if (StringUtils.isBlank(label))
			return StringUtils.EMPTY;
		else
			return String.format("COMMENT ON COLUMN %s.%s IS '%s'", entityName, name, StringEscapeUtils.escapeSql(label));
	}

	@Override
	public String entityName() {
		return entityName;
	}

	@Override
	public Integer order() {
		return order;
	}

	@Override
	public Entry<String, Object> entryOf(Object value) {
		return new EntryImpl<>(name(), value);
	}

	@Override
	public Object cast(String value) {
		if (StringUtils.isBlank(value))
			return null;
		
		return type.cast(value);
	}

	@Override
	public boolean belongTo(Type type) {
		return this.type.belongTo(type);
	}
}
