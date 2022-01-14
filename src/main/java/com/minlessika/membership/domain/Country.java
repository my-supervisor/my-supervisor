package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		name="base_country", 
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
