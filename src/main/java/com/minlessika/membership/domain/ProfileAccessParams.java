package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface ProfileAccessParams extends DomainSet<ProfileAccessParam, ProfileAccessParams> {
	ProfileAccessParam put(AccessParam param, String value) throws IOException;
}
