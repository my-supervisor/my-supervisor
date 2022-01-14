package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface IndicatorTemplates extends DomainSet<IndicatorTemplate, IndicatorTemplates> {
	IndicatorTemplate generate(Indicator indicator) throws IOException;
}
