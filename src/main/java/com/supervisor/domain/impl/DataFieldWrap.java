package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataModel;

public class DataFieldWrap implements DataField {

	protected final DataField origin;
	
	public DataFieldWrap(final DataField origin) {
		this.origin = origin;
	}

	@Override
	public DataModel model() throws IOException {
		return origin.model();
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
	public DataFieldType type() throws IOException {
		return origin.type();
	}

	@Override
	public DataFieldStyle style() throws IOException {
		return origin.style();
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
			throws IOException {
		origin.update(code, name, type, style, description);
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
	public DataFieldDependencies dependencies() throws IOException {
		return origin.dependencies();
	}
}
