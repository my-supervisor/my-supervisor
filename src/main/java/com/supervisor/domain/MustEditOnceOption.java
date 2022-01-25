package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;

public interface MustEditOnceOption {

	@Field(label="Doit éditer une seule fois ?")
	boolean mustEditOnce() throws IOException;
	
	void makeMustEditOnce(boolean mustEditOnce) throws IOException;
}
