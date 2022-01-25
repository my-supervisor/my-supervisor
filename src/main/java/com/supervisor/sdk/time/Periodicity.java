package com.supervisor.sdk.time;

import com.supervisor.sdk.metadata.Field;

import java.time.LocalDate;

public interface Periodicity {	
	
	@Field(label="Nombre")
	int number();
	
	@Field(label="Unité")
	PeriodicityUnit unit();
	
	@Field(label="Référence")
	LocalDate reference();
	
	@Field(label="Fermer intervalle ?")
	boolean closeInterval();
	
	LocalDate begin(LocalDate date);
	LocalDate end(LocalDate date);
}
