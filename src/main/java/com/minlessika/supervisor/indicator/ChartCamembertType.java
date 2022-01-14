package com.minlessika.supervisor.indicator;

public enum ChartCamembertType {
	
	NONE("Non défini", "non-defini"),
	PIE("Pie", "pie"),
	DONUT("Donut", "donut");
	
	private final String label;
	private final String shortName;
	
	ChartCamembertType(final String label, final String shortName){
		this.label = label;
		this.shortName = shortName;
	}
	
	public String shortName() {
		return shortName;
	}
	
	public String toString() {
		return label;
	}
}
