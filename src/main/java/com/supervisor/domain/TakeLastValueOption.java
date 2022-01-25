package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;

public interface TakeLastValueOption {

	@Field(label="Prendre dernière valeur ?")
	boolean takeLastValue() throws IOException;
	
	void takeLastValue(boolean takeLastValue) throws IOException;
}
