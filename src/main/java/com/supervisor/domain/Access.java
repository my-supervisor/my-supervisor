package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
		name="membership_access", 
		label="Droit d'accès"
)
public interface Access extends Recordable {
	
	@Field(label="Code")
	String code() throws IOException; 
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	int intParamValueOf(String code) throws IOException;
	String paramValueOf(String code) throws IOException;
	
	AccessParams parameters() throws IOException;
	
	void update(String code, String name) throws IOException;
}
