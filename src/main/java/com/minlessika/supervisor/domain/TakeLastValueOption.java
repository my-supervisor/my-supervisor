package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;

public interface TakeLastValueOption {

	@Field(label="Prendre dernière valeur ?")
	boolean takeLastValue() throws IOException;
	
	void takeLastValue(boolean takeLastValue) throws IOException;
}
