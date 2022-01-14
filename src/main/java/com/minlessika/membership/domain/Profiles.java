package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Profiles extends DomainSet<Profile, Profiles>  {
	
	Profile add(String code, String name) throws IOException;
	
	Profile admin() throws IOException;
	Profile anonymous() throws IOException;
	Profile simpleUser() throws IOException;
	
	Profile get(String code) throws IOException;
	Profile getByTag(String tag) throws IOException;
}
