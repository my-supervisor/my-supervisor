package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	name="base_event", 
	label="Event"
)
public interface Event extends Recordable {

	@Field(label="Identifier")
	String identifier() throws IOException;
	
	@Field(label="Level")
	EventLevel level() throws IOException;
	
	@Field(label="Information")
	String information() throws IOException;
	
	@Field(label="Details")
	String details() throws IOException;
}
