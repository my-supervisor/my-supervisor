package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
	name="base_currency", 
	label="Devise"
)
public interface Currency extends Recordable {
	
	@Field(label="Code")
	String code() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Symbole")
	String symbol() throws IOException;
	
	@Field(label="Symbole positionné après ?")
	boolean after() throws IOException;
	
	@Field(label="Précision")
	int precision() throws IOException;
	
	double convert(double amount, Currency currency) throws IOException;
	
	void update(String code, String name, String symbol, boolean after, int precision) throws IOException;
	
	String toString(double amount) throws IOException;
}
