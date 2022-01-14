package com.minlessika.sdk.datasource;

import org.apache.commons.lang.StringUtils;

public interface Ordering {
	
	String script();
	Ordering reverse();
	
	Ordering EMPTY = new Ordering() {
		
		@Override
		public String script() {
			return StringUtils.EMPTY;
		}

		@Override
		public Ordering reverse() {
			return Ordering.EMPTY;
		}
	};
}
