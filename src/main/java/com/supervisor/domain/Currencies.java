package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Currencies extends DomainSet<Currency, Currencies>  {
	Currency add(String code, String name, String symbol) throws IOException;
}
