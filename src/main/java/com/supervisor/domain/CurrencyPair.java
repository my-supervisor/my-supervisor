package com.supervisor.domain;

import java.io.IOException;

public interface CurrencyPair {
	
	Currency base();
	Currency counterParty();
	double rate();
	
	double convert(double amount) throws IOException;	
	CurrencyPair invert() throws IOException;
}
