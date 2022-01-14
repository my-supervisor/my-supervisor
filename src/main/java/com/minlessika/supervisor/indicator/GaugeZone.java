package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.colors.Color;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_gauge_zone", 
		label="Zone d'un Jauge"
)
public interface GaugeZone extends Recordable {

	@Field(
		label="Gauge", 
		rel=Relation.MANY2ONE
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
