package com.minlessika.supervisor.domain.bi;

import java.time.LocalDate;
import com.minlessika.sdk.datasource.Ordering;
import com.minlessika.sdk.time.Period;

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
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BiRequest with(String filter) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BiRequest to(LocalDate now) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long start() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public BiSelects selects() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BiPeriod period() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Ordering ordering() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BiRequest of(LocalDate begin, LocalDate end) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int limit() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String filter() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BiColumns columns() {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
