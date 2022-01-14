package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.sdk.utils.CodeContainer;

public interface Indicators extends DomainSet<Indicator, Indicators>, CodeContainer {	
	Indicator add(String code, String shortName) throws IOException;
}
