package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;

import java.io.IOException;
import java.time.LocalDate;

@com.minlessika.sdk.metadata.Recordable(
	name="base_sequence", 
	label="Séquence"
)
public interface Sequence extends Recordable {
	
	@Field(label="Code")
	String code() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Préfixe", isMandatory=false)
	String prefix() throws IOException;
	
	@Field(label="Suffixe", isMandatory=false)
	String suffix() throws IOException;
	
	@Field(label="Taille")
	int size() throws IOException;
	
	@Field(label="Pas")
	int step() throws IOException;
	
	@Field(label="Prochain nombre")
	long nextNumber() throws IOException;
		
	String generate() throws IOException;
	
	void define(String name, String prefix, String suffix, int size, int step, long nextNumber) throws IOException;
	Sequence withReference(LocalDate date) throws IOException;
}
