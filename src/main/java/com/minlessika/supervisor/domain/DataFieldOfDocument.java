package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable()
public interface DataFieldOfDocument {
	
	@Field(name="no", label="Ordre", forceInherit=true)
	int order() throws IOException;
	
	@Field(label="Est obligatoire ?", forceInherit=true)
	boolean isMandatory() throws IOException;
}
