package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;

public interface ReadOnlyOption {
	
	@Field(label="Est en lecture seule ?")
	boolean isReadOnly() throws IOException;
	
	void makeReadOnly(boolean readOnly) throws IOException;

}
