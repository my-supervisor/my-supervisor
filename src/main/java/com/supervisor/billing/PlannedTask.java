package com.supervisor.billing;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@com.supervisor.sdk.metadata.Recordable(
	name="planned_task",
	label="Planned task"
)
public interface PlannedTask extends Recordable {
	
	@Field(label="Type")
	PlannedTaskType type() throws IOException;
	
	@Field(label="Planned date")
	LocalDateTime startDate() throws IOException;
	
	@Field(label="Description")
	String description() throws IOException;
	
	@Field(label="Metadata")
	Map<String, String> metadata() throws IOException;
	
	@Field(label="Status")
	PlannedTaskStatus status() throws IOException;
	
	void cancel() throws IOException;
	void execute() throws IOException;
}
