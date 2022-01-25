package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.colors.Color;
import com.supervisor.sdk.datasource.DomainSet;

public interface GaugeZones extends DomainSet<GaugeZone, GaugeZones> {
	GaugeZone add(Color color, double min, double max) throws IOException;
}
