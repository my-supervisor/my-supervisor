package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.colors.Color;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
		name="gauge_zone",
		label="Zone d'un Jauge"
)
public interface GaugeZone extends com.supervisor.sdk.datasource.Recordable {

	@Field(
		label="Gauge", 
		rel= Relation.MANY2ONE
	)
	Gauge gauge() throws IOException;
	
	@Field(label="Couleur")
    Color color() throws IOException;
	
	@Field(label="Minimum")
	double min() throws IOException;
	
	@Field(label="Maximum")
	double max() throws IOException;
	
	void update(Color color, double min, double max) throws IOException;
}
