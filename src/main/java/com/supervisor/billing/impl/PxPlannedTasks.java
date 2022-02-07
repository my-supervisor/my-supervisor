package com.supervisor.billing.impl;

import com.supervisor.billing.PlannedTask;
import com.supervisor.billing.PlannedTaskStatus;
import com.supervisor.billing.PlannedTaskType;
import com.supervisor.billing.PlannedTasks;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Matchers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public final class PxPlannedTasks extends DomainRecordables<PlannedTask, PlannedTasks> implements PlannedTasks {

	private final User user;
	
	public PxPlannedTasks(final User user) throws IOException {
		this(user.base().select(PlannedTask.class), user);
	}
	
	private PxPlannedTasks(final RecordSet<PlannedTask> source, final User user) throws IOException {
		super(PxPlannedTask.class, source);
		
		this.user = user;
		this.source = source.where(PlannedTask::ownerId, user.id())
				            .orderBy(PlannedTask::id, OrderDirection.ASC);
	}

	@Override
	protected PlannedTasks domainSetOf(final RecordSet<PlannedTask> source) throws IOException {
		return new PxPlannedTasks(source, user);
	}
	
	@Override
	public PlannedTask planDirectTask(Map<String, String> metadata, String description) throws IOException {
		Record<PlannedTask> record = source.entryOf(PlannedTask::description, description)
									       .entryOf(PlannedTask::status, PlannedTaskStatus.PENDING)
									       .entryOf(PlannedTask::metadata, metadata)
									       .entryOf(PlannedTask::startDate, LocalDateTime.now())
									       .entryOf(PlannedTask::type, PlannedTaskType.DIRECT)
									       .addForUser(user.id());
		
		return domainOf(record);
	}

	@Override
	public PlannedTask planDelayedTask(LocalDateTime startDate, Map<String, String> metadata, String description) throws IOException {
		Record<PlannedTask> record = source.entryOf(PlannedTask::description, description)
									       .entryOf(PlannedTask::status, PlannedTaskStatus.PENDING)
									       .entryOf(PlannedTask::metadata, metadata)
									       .entryOf(PlannedTask::startDate, startDate)
									       .entryOf(PlannedTask::type, PlannedTaskType.DELAYED)
									       .addForUser(user.id());

		return domainOf(record);
	}

	@Override
	public PlannedTasks tasksToExecute(LocalDateTime startDate) throws IOException {
		return this.where(PlannedTask::startDate, Matchers.lessOrEqualsTo(startDate))
				   .where(PlannedTask::status, PlannedTaskStatus.PENDING);
	}

}
