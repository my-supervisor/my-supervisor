package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Application;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@com.minlessika.sdk.metadata.Recordable(
	name="base_planned_task", 
	label="Planned task"
)
public interface PlannedTask extends Recordable {
	
	@Field(label="Type")
	PlannedTaskType type() throws IOException;
	
	@Field(label="Planned date")
	LocalDateTime startDate() throws IOException;
	
	@Field(
		label="Application",
		rel=Relation.MANY2ONE
	)
	Application application() throws IOException;
	
	@Field(label="Description")
	String description() throws IOException;
	
	@Field(label="Metadata")
	Map<String, String> metadata() throws IOException;
	
	@Field(label="Status")
	PlannedTaskStatus status() throws IOException;
	
	void cancel() throws IOException;
	void execute() throws IOException;
}
