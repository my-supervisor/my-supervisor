package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface IndicatorTemplates extends DomainSet<IndicatorTemplate, IndicatorTemplates> {
	IndicatorTemplate generate(Indicator indicator) throws IOException;
}
