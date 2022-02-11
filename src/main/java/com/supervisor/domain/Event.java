package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
	name="event",
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
