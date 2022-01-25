package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface AllAccesses extends DomainSet<Access, AllAccesses> {
	Access access(String code) throws IOException;
}
