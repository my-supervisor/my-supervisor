package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.colors.Color;
import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_goal_number_setting", 
		label="Configuration d'un chiffre objectif",
		comodel=Indicator.class
)
public interface GoalNumber extends Indicator {
	
	@Field(label="Couleur")
	Color color() throws IOException;
	
	@Field(label="Symbole de l'unité")
	String unitySymbol() throws IOException;
	
	@Field(label="Position du symbole")
	SymbolPosition symbolPosition() throws IOException;
	
	void update(
			String code, 
			Color color,
			String unitySymbol, 
			SymbolPosition symbolPosition, 
			String label, 
			String description
	) throws IOException;
	
	String label() throws IOException;
	double number() throws IOException;
	double goal() throws IOException;
	int numberInPercent() throws IOException;
}
