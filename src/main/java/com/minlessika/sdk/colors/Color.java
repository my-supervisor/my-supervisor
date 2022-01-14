package com.minlessika.sdk.colors;

public enum Color {
	
	NONE("Non défini", "non-defini"),
	WHITE("White", "#FFFFFF"),
	GREEN("Green", "rgb(44, 160, 44)"),
	ORANGE("Orange", "#FFC300"),
	RED("Red", "#FF0000"),
	LIME("Lime", "#00FF00"),
	YELLOW("Yellow", "#FFFF00"),
	BLUE("Blue", "#0000FF"),
	FUCHSIA("Fuchsia", "#FF00FF"),
	SILVER("Silver", "#C0C0C0"),
	MAROON("Maroon", "#800000");
	
	private final String label;
	private final String code;
	
	Color(final String label, final String code){
		this.label = label;
		this.code = code;
	}
	
	public String code() {
		return code;
	}
	
	public String toString() {
		return label;
	}
}
