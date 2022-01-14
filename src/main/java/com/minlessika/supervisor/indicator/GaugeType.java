package com.minlessika.supervisor.indicator;

public enum GaugeType {
	
	NONE("Non défini", "non-defini"),
	RADIAL("Radial", "radial-gauge"),
	LINEAR("Linear", "linear-gauge");
	
	private final String label;
	private final String shortName;
	
	GaugeType(final String label, final String shortName){
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
