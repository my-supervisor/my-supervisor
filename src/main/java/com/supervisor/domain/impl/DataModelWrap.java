package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModels;
import com.supervisor.domain.DataFields;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.indicator.Indicators;

public class DataModelWrap implements DataModel {

	protected final DataModel origin;
	
	public DataModelWrap(final DataModel origin) {
		this.origin = origin;
	}
	
	@Override
	public Long id() {
		return origin.id();
	}

	@Override
	public UUID guid() throws IOException {
		return origin.guid();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return origin.creationDate();
	}

	@Override
	public Long creatorId() throws IOException {
		return origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return origin.lastModificationDate();
	}

	@Override
	public Long lastModifierId() throws IOException {
		return origin.lastModifierId();
	}

	@Override
	public Long ownerId() throws IOException {
		return origin.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return origin.tag();
	}

	@Override
	public Base base() {
		return origin.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return origin.listOf(clazz);
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return origin.listOf(clazz, viewScript);
	}

	@Override
	public Activity activity() throws IOException {
		return origin.activity();
	}

	@Override
	public DataModelType type() throws IOException {
		return origin.type();
	}

	@Override
	public String code() throws IOException {
		return origin.code();
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public String description() throws IOException {
		return origin.description();
	}

	@Override
	public DataFields fields() throws IOException {
		return origin.fields();
	}

	@Override
	public boolean active() throws IOException {
		return origin.active();
	}

	@Override
	public void update(String code, String name, String description) throws IOException {
		origin.update(code, name, description);
	}

	@Override
	public void activate(boolean active) throws IOException {
		origin.activate(active);
	}

	@Override
	public boolean isTemplate() throws IOException {
		return origin.isTemplate();
	}

	@Override
	public void templating(boolean template) throws IOException {
		origin.templating(template);
	}

	@Override
	public Indicators indicatorsThatDependsOn() throws IOException {
		return origin.indicatorsThatDependsOn();
	}

	@Override
	public boolean dependsOn(DataModel model) throws IOException {
		return origin.dependsOn(model);
	}

	@Override
	public boolean strictDependsOn(DataModel model) throws IOException {
		return origin.strictDependsOn(model);
	}

	@Override
	public AggregatedModels aggregatedModels() throws IOException {
		return origin.aggregatedModels();
	}

	@Override
	public boolean interacting() throws IOException {
		return origin.interacting();
	}

}
