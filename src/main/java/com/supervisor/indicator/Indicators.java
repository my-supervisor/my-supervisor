package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.sdk.utils.CodeContainer;

public interface Indicators extends DomainSet<Indicator, Indicators>, CodeContainer {
	Indicator add(String code, String shortName) throws IOException;
}
