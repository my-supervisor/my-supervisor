package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
		name="access_param",
		label="Paramètre de droit d'accès"
)
public interface AccessParam extends Recordable {
	
	@Field(
		label="Droit d'accès", 
		rel=Relation.MANY2ONE
	)
	Access access() throws IOException;
	
	@Field(label="Code")
	String code() throws IOException; 
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Valeur par défaut")
	String defaultValue() throws IOException;
	
	@Field(label="Type de la valeur")
	AccessParamValueType valueType() throws IOException;
	
	@Field(label="Quantificateur")
	AccessParamQuantifier quantifier() throws IOException;
	
	@Field(label="Message")
	String message() throws IOException;
	
	boolean isValid(String value) throws IOException;
	
	void update(String code, String name, String defaultValue, AccessParamQuantifier quantifier, String message) throws IOException;
}
