package com.supervisor.domain.bi;

import java.util.List;

public interface BiRow {
	
	BiResult result();
	List<BiValue> cells();
	BiValue get(String name);
	
	BiValue addCell(String name, Object value);
}
