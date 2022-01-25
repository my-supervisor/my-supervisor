package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface WhenCases extends DomainSet<WhenCase, WhenCases> {

	WhenCase add() throws IOException;
}
