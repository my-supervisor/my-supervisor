package com.minlessika.sdk.metadata;

import java.lang.reflect.Type;
import java.util.Map.Entry;

public class FieldMetadataWrap implements FieldMetadata {

	protected final FieldMetadata origin;
	
	public FieldMetadataWrap(final FieldMetadata origin) {
		this.origin = origin;
	}
	
	@Override
	public Integer order() {
		return origin.order();
	}

	@Override
	public String name() {
		return origin.name();
	}

	@Override
	public BaseType type() {
		return origin.type();
	}

	@Override
	public String label() {
		return origin.label();
	}

	@Override
	public String entityName() {
		return origin.entityName();
	}

	@Override
	public String definitionScript() {
		return origin.definitionScript();
	}

	@Override
	public String constraintScript() {
		return origin.constraintScript();
	}

	@Override
	public String commentScript() {
		return origin.commentScript();
	}

	@Override
	public Entry<String, Object> entryOf(Object value) {
		return origin.entryOf(value);
	}

	@Override
	public boolean belongTo(Type type) {
		return origin.belongTo(type);
	}

	@Override
	public Object cast(String value) {
		return origin.cast(value);
	}
}
