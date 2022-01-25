package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Accesses extends DomainSet<Access, Accesses> {
	Access add(String code, String name) throws IOException;
	Access access(String code) throws IOException;
}
