package com.supervisor.sdk.metadata;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class BasicType implements BaseType {

	final String name;
	final Type type;
	final List<Type> similarTypes;
	
	public BasicType(final String name, final Type type) {
		this(name, type, Arrays.asList());
	}
	
	public BasicType(final String name, final Type type, final List<Type> similarTypes) {
		this.name = name;
		this.type = type;
		this.similarTypes = similarTypes;
	}
	
	@Override
	public String name() {
		return this.name;
	}

	@Override
	public Type type() {
		return this.type;
	}

	@Override
	public Object cast(String value) {
		return value;
	}

	@Override
	public List<Type> similarTypes() {
		return similarTypes;
	}

	@Override
	public boolean belongTo(Type type) {
		return this.type == type || similarTypes.contains(type);
	}

}
