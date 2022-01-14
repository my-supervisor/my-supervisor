package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.PlannedTask;
import com.minlessika.membership.billing.PlannedTaskStatus;
import com.minlessika.membership.billing.PlannedTaskType;
import com.minlessika.membership.billing.PlannedTasks;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.comparators.Matchers;

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
		
		if(!metadata.containsKey(Application.class.getSimpleName()))
			throw new IllegalArgumentException("Planned Task : Application ID must be specified in metadata !");
		
		final Long applicationId = Long.parseLong(metadata.get(Application.class.getSimpleName()));		
		Record<PlannedTask> record = source.entryOf(PlannedTask::application, applicationId)
									       .entryOf(PlannedTask::description, description)
									       .entryOf(PlannedTask::status, PlannedTaskStatus.PENDING)
									       .entryOf(PlannedTask::metadata, metadata)
									       .entryOf(PlannedTask::startDate, LocalDateTime.now())
									       .entryOf(PlannedTask::type, PlannedTaskType.DIRECT)
									       .addForUser(user.id());
		
		return domainOf(record);
	}

	@Override
	public PlannedTask planDelayedTask(LocalDateTime startDate, Map<String, String> metadata, String description) throws IOException {
		
		if(!metadata.containsKey(Application.class.getSimpleName()))
			throw new IllegalArgumentException("Planned Task : Application ID must be specified in metadata !");
		
		final Long applicationId = Long.parseLong(metadata.get(Application.class.getSimpleName()));	
		Record<PlannedTask> record = source.entryOf(PlannedTask::application, applicationId)
									       .entryOf(PlannedTask::description, description)
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
				   .where(PlannedTask::status, PlannedTaskStatus.PENDING)
				   .where(PlannedTask::application, user.applications().current().id());
	}

}
