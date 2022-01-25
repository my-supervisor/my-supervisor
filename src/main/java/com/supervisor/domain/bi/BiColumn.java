package com.supervisor.domain.bi;

public interface BiColumn {	
	String name();
	String body();
	AggregateFunc aggregate();
}
