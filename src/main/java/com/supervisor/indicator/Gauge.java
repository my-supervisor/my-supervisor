package com.supervisor.indicator;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="gauge_setting",
		label="Configuration d'un Jauge",
		comodel=Indicator.class
)
public interface Gauge extends Indicator {
	
	@Field(label="Symbole de l'unité")
	String unitySymbol() throws IOException;
	
	@Field(label="Position du symbole")
	SymbolPosition symbolPosition() throws IOException;
	
	@Field(label="Type de jauge")
	GaugeType gaugeType() throws IOException;
	
	@Field(label="Division mineure")
	int minorTicks() throws IOException;
	
	@Field(label="Division majeure")
	int majorTicks() throws IOException;
	
	@Field(label="Minimum")
	double min() throws IOException;
	
	@Field(label="Maximum")
	double max() throws IOException;
	
	GaugeZones zones() throws IOException;
	
	void graduate(int minor, int major) throws IOException;
	void limit(double min, double max) throws IOException;
	
	void update(String code, GaugeType gaugeType, String unitySymbol, SymbolPosition symbolPosition, String label, String description) throws IOException;
		
	String label() throws IOException;
	double number() throws IOException;
	List<Double> graduations() throws IOException;
}
