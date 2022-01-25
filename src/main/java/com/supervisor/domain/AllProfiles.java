package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface AllProfiles extends DomainSet<Profile, AllProfiles>  {
	Profile get(String code, String module) throws IOException;
	Profile getByTag(String tag, String module) throws IOException;
}
