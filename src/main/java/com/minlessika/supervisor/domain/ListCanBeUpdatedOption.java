package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;

public interface ListCanBeUpdatedOption {
	
	@Field(label="Peut être mise à jour ?")
	boolean canBeUpdated() throws IOException;
	
	void makeCanBeUpdated(boolean update) throws IOException;

}
