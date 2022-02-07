package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface AllProfiles extends DomainSet<Profile, AllProfiles>  {
	Profile get(String code) throws IOException;
}
