package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface ProfileAccesses extends DomainSet<ProfileAccess, ProfileAccesses> {
	ProfileAccess add(Access access) throws IOException;
	
	boolean hasAccess(String code) throws IOException;
	boolean hasAccess(String code, String value) throws IOException;
	void validateAccessibility(String code) throws IOException;
	void validateAccessibility(String code, String value) throws IOException;
	
	Accesses exceptedAccesses() throws IOException;
}
