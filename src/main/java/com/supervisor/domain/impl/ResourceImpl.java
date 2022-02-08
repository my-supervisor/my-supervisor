package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Resource;
import com.supervisor.domain.ResourceType;
import com.supervisor.indicator.Indicator;

public final class ResourceImpl implements Resource {

	private final String name;
	private final ResourceType type;
	private final Recordable record;
	
	public ResourceImpl(final DataSheetModel model) throws IOException {
		this(model.name(), ResourceType.DATA_SHEET_MODEL, model);
	}
	
	public ResourceImpl(final Activity activity) throws IOException {
		this(activity.name(), ResourceType.ACTIVITY, activity);
	}
	
	public ResourceImpl(final Indicator indicator) throws IOException {
		this(indicator.name(), ResourceType.INDICATOR, indicator);
	}
	
	public ResourceImpl(final String name, final ResourceType type, final Recordable record) {
		this.name = name;
		this.type = type;
		this.record = record;
	}
	
	@Override
	public UUID id() {
		return record.id();
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ResourceType type() {
		return type;
	}

	@Override
	public UUID ownerId() throws IOException {
		return record.ownerId();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return record.creationDate();
	}

	@Override
	public UUID creatorId() throws IOException {
		return record.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return record.lastModificationDate();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return record.lastModifierId();
	}

	@Override
	public String tag() throws IOException {
		return record.tag();
	}

	@Override
	public Base base() {
		return record.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return record.listOf(clazz);
	}
	
	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return record.listOf(clazz, viewScript);
	}
}
