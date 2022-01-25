package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Addresses extends DomainSet<Address, Addresses> {
	Address add(String email, Country country) throws IOException;
}
