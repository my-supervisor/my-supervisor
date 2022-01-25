package com.supervisor.domain.bi;

import java.time.LocalDate;
import com.supervisor.sdk.datasource.Ordering;

public interface BiRequest {
	
	String filter();
	BiColumns columns();
	BiSelects selects();
	BiPeriod period();
	long start();
	int limit();
	
	Ordering ordering();
	
	BiRequest of(LocalDate begin, LocalDate end);
	BiRequest to(LocalDate now);
	BiRequest with(String filter);
	BiRequest with(long start, int limit);	
	
	final BiRequest EMPTY = new BiRequest() {
		
		@Override
		public BiRequest with(long start, int limit) {
			
			return null;
		}
		
		@Override
		public BiRequest with(String filter) {
			
			return null;
		}
		
		@Override
		public BiRequest to(LocalDate now) {
			
			return null;
		}
		
		@Override
		public long start() {
			
			return 0;
		}
		
		@Override
		public BiSelects selects() {
			
			return null;
		}
		
		@Override
		public BiPeriod period() {
			
			return null;
		}

		@Override
		public Ordering ordering() {
			
			return null;
		}
		
		@Override
		public BiRequest of(LocalDate begin, LocalDate end) {
			
			return null;
		}
		
		@Override
		public int limit() {
			
			return 0;
		}
		
		@Override
		public String filter() {
			
			return null;
		}

		@Override
		public BiColumns columns() {
			
			return null;
		}
	};
}
