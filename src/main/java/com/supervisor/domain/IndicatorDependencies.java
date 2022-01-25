package com.supervisor.domain;

import java.io.IOException;
import java.util.List;

import com.supervisor.indicator.Indicator;

public interface IndicatorDependencies {
	List<Indicator> items() throws IOException;
}
