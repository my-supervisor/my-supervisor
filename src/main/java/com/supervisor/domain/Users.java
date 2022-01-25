package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;
import java.util.Optional;

public interface Users extends DomainSet<User, Users> {
	
	User registerSecure(final Person identity, final String encryptedPassword, final String salt) throws IOException;
	User registerUnSecure(final Person identity, final String password, final String confirmedPassword) throws IOException;
	
	User signin(String email, String password, boolean secure) throws IOException;
	Optional<User> userOf(String email) throws IOException;
}
