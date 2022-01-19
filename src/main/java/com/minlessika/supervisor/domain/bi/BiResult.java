package com.minlessika.supervisor.domain.bi;

import java.util.ArrayList;
import java.util.List;

public interface BiResult {
	
	List<BiRow> rows();
	long count();
	
	BiRow addRow();
	
	final BiResult EMPTY = new BiResult() {
		
		@Override
		public List<BiRow> rows() {
			return new ArrayList<>();
		}
		
		@Override
		public long count() {
			return 0;
		}
		
		@Override
		public BiRow addRow() {
			
			return null;
		}
	};
}
