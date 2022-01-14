package com.minlessika.core.takes;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.utils.Version;

import java.io.IOException;
import java.time.LocalDate;

@com.minlessika.sdk.metadata.Recordable(
		name="base_version", 
		label="Version de la BDD"
)
public interface BddVersion extends Recordable, Version {
	
	@Field(label="Date de sortie")
	String releaseDate() throws IOException;
	
	@Field(label="N° de version")
	String revision() throws IOException;
	
	@Field(label="Gamme du produit")
	String productRange() throws IOException;
	
	@Field(label="Notes")
	String notes() throws IOException;
	
	void update(String revision, LocalDate releaseDate, String notes) throws IOException;
}
