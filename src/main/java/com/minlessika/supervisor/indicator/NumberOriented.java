package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_number_oriented_setting", 
		label="Configuration d'un chiffre orienté",
		comodel=Indicator.class
)
public interface NumberOriented extends Indicator {
	
	@Field(label="Symbole de l'unité")
	String unitySymbol() throws IOException;
	
	@Field(label="Position du symbole")
	SymbolPosition symbolPosition() throws IOException;
	
	@Field(label="Gérer le pourcentage d'évolution")
	boolean manageEvolutionPercent() throws IOException;
	
	void update( 
			String code,
			String singleLabel,
			String pluralLabel,
			boolean manageEvolutionPercent, 
			String unitySymbol, 
			SymbolPosition symbolPosition, 
			String description
	) throws IOException;
	
	String label() throws IOException;
	double number() throws IOException;
	double increaseRate() throws IOException;
	int increaseInPercent() throws IOException;
}
