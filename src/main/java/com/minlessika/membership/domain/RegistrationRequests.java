package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface RegistrationRequests extends DomainSet<RegistrationRequest, RegistrationRequests> {
	RegistrationRequest request(String name, String email, String password, String confirmedPassword, Language preferredLanguage) throws IOException;
}
