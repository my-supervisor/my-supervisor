package com.minlessika.sdk.metadata;

import java.lang.reflect.Type;
import java.util.Map.Entry;

public interface FieldMetadata {
	
	Integer order();
	String name();
	BaseType type();
	String label();
	String entityName();
	
	String definitionScript();
	String constraintScript();
	String commentScript();
	
	Entry<String, Object> entryOf(Object value);
	
	boolean belongTo(Type type);
	Object cast(String value);
}
