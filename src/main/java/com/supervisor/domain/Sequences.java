package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Sequences extends DomainSet<Sequence, Sequences>  {
	Sequence add(String code, String name, String prefix, String suffix, int size, int step, long nextNumber) throws IOException;
	Sequence get(String code) throws IOException;	
}
