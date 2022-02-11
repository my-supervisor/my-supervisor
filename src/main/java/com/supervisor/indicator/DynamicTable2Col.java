package com.supervisor.indicator;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="dynamic_table_2_col",
		label="Configuration d'un tableau dynamique à 2 colonnes",
		comodel=Indicator.class
)
public interface DynamicTable2Col extends Indicator {
	
	String label() throws IOException;
	
	@Field(label="Symbole de l'unité")
	String unitySymbol() throws IOException;
	
	@Field(label="Position du symbole")
	SymbolPosition symbolPosition() throws IOException;
	
	@Field(label="Mode de classement")
	OrderingMode orderingMode() throws IOException;
	
	@Field(label="Nombre maxi d'éléments à afficher (<= 0 pour tout)")
	int nbMaxOfElementsToShow() throws IOException;
	
	@Field(label="Gérer le pourcentage d'évolution")
	boolean manageEvolutionPercent() throws IOException;
	
	void update( 
			String code,
			String label,
			boolean manageEvolutionPercent, 
			String unitySymbol, 
			SymbolPosition symbolPosition, 
			OrderingMode orderingMode,
			int nbMaxOfElementsToShow,
			String description
	) throws IOException;
	
	List<String> names() throws IOException;
	List<Double> values() throws IOException;
	
	List<Double> increaseRates() throws IOException;
	List<Integer> increaseInPercents() throws IOException;
}
