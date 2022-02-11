package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
		name="country",
		label="Pays"
)
public interface Country extends Recordable {
	
	@Field(label="Code")
	String code() throws IOException; 
	
	@Field(label="Nom")
	String name() throws IOException; 
	
	@Field(
		label="Devise",
		rel=Relation.MANY2ONE
	)
	Currency currency() throws IOException;
	
	@Field(label="Indicatif")
	int phoneCode() throws IOException;
}
