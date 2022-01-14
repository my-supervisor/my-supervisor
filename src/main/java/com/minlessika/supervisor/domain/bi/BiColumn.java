package com.minlessika.supervisor.domain.bi;

public interface BiColumn {	
	String name();
	String body();
	AggregateFunc aggregate();
}
