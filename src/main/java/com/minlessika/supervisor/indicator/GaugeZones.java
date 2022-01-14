package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.colors.Color;
import com.minlessika.sdk.datasource.DomainSet;

public interface GaugeZones extends DomainSet<GaugeZone, GaugeZones> {
	GaugeZone add(Color color, double min, double max) throws IOException;
}
