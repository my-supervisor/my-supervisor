package com.supervisor.billing;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public interface PlannedTasks extends DomainSet<PlannedTask, PlannedTasks> {

	PlannedTask planDirectTask(Map<String, String> metadata, String description) throws IOException;
	PlannedTask planDelayedTask(LocalDateTime startDate, Map<String, String> metadata, String description) throws IOException;
	
	PlannedTasks tasksToExecute(LocalDateTime startDate) throws IOException;
}
