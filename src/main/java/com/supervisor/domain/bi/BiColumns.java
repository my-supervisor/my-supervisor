package com.supervisor.domain.bi;

import java.util.List;

public interface BiColumns {
	
	List<BiColumn> items();
	boolean canGroup();
	
	void add(BiColumn column);
}
