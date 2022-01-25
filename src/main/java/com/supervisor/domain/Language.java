package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
		name="base_language", 
		label="Langue"
)
public interface Language extends Recordable {
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Code")
	String code() throws IOException;
	
	@Field(label="Iso code")
	String isoCode() throws IOException;
	
	@Field(label="Activé")
	boolean enabled() throws IOException;
}
