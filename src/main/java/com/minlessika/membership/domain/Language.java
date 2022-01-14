package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
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
