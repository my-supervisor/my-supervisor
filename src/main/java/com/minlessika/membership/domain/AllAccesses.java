package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface AllAccesses extends DomainSet<Access, AllAccesses> {
	Access access(String code) throws IOException;
}
