package com.supervisor.indicator;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="chart_camembert_setting",
		label="Configuration d'un camembert",
		comodel=Indicator.class
)
public interface ChartCamembert extends Indicator {
	
	@Field(label="Type de camembert")
	ChartCamembertType camembertType() throws IOException;
	
	void update(
			String code, 
			ChartCamembertType camembertType, 
			String label, 
			String subLabel, 
			String description
	) throws IOException;
		
	String label() throws IOException;
	
	@Field(label="Sous-titre", isMandatory=false)
	String subLabel() throws IOException;
	
	List<String> columns() throws IOException;
	List<String> names() throws IOException;
	List<Double> values() throws IOException;
}
