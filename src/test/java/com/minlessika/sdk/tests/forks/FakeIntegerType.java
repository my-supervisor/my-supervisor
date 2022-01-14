package com.minlessika.sdk.tests.forks;

import com.minlessika.sdk.metadata.BaseType;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class FakeIntegerType implements BaseType {

	@Override
	public String name() {
		return "integer";
	}

	@Override
	public Type type() {
		return Integer.class;
	}

	@Override
	public Object cast(String value) {
		return Integer.parseInt(value);
	}

	@Override
	public List<Type> similarTypes() {
		return Arrays.asList(int.class);
	}

	@Override
	public boolean belongTo(Type type) {
		return this.type() == type || similarTypes().contains(type);
	}
}
