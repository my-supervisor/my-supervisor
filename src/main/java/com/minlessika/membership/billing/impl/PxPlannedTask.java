package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.PlannedTask;
import com.minlessika.membership.billing.PlannedTaskStatus;
import com.minlessika.membership.billing.PlannedTaskType;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.impl.PxApplication;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public final class PxPlannedTask extends DomainRecordable<PlannedTask> implements PlannedTask {

	public PxPlannedTask(Record<PlannedTask> record) {
		super(record);
	}

	@Override
	public PlannedTaskType type() throws IOException {
		return record.valueOf(PlannedTask::type);
	}

	@Override
	public LocalDateTime startDate() throws IOException {
		return record.valueOf(PlannedTask::startDate);
	}

	@Override
	public Application application() throws IOException {
		return new PxApplication(record.of(PlannedTask::application));
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(PlannedTask::description);
	}

	@Override
	public Map<String, String> metadata() throws IOException {
		return record.valueOf(PlannedTask::metadata);
	}

	@Override
	public PlannedTaskStatus status() throws IOException {
		return record.valueOf(PlannedTask::status);
	}

	@Override
	public void cancel() throws IOException {
		
		if(status() != PlannedTaskStatus.PENDING)
			throw new IllegalArgumentException("Task can not be cancelled !");
		
		record.entryOf(PlannedTask::status, PlannedTaskStatus.CANCELLED)
		      .update();
	}

	@Override
	public void execute() throws IOException {
		
		if(status() != PlannedTaskStatus.PENDING)
			throw new IllegalArgumentException("Task can not be cancelled !");
		
		record.entryOf(PlannedTask::status, PlannedTaskStatus.EXECUTED)
		      .update(); 
	}

}
