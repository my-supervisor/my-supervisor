package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface RegistrationRequests extends DomainSet<RegistrationRequest, RegistrationRequests> {
	RegistrationRequest request(String name, String email, String password, String confirmedPassword, Language preferredLanguage) throws IOException;
}
