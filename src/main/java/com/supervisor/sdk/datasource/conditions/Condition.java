package com.supervisor.sdk.datasource.conditions;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface Condition {
	String toScritpt() throws IOException;
	List<Object> parameters() throws IOException;
	
	Condition EMPTY = new Condition() {
		
		@Override
		public String toScritpt() throws IOException {
			return StringUtils.EMPTY;
		}
		
		@Override
		public List<Object> parameters() throws IOException {
			return Arrays.asList();
		}
	};
}
