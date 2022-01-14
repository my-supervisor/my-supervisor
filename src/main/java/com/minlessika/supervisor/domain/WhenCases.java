package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface WhenCases extends DomainSet<WhenCase, WhenCases> {

	WhenCase add() throws IOException;
}
