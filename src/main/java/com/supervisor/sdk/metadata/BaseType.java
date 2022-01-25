package com.supervisor.sdk.metadata;

import java.lang.reflect.Type;
import java.util.List;

public interface BaseType {
	String name();
	Type type();
	
	List<Type> similarTypes();
	
	boolean belongTo(Type type);
	Object cast(String value);
}
