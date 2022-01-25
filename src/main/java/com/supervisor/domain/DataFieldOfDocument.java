package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable()
public interface DataFieldOfDocument {
	
	@Field(name="no", label="Ordre", forceInherit=true)
	int order() throws IOException;
	
	@Field(label="Est obligatoire ?", forceInherit=true)
	boolean isMandatory() throws IOException;
}
