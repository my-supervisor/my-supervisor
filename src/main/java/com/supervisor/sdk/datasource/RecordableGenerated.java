package com.supervisor.sdk.datasource;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class RecordableGenerated implements Recordable {

	protected final Base base;
	
	public RecordableGenerated(Base base) {
		this.base = base;
	}
	
	@Override
	public UUID id() {
		return null;
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return LocalDateTime.now();
	}

	@Override
	public UUID creatorId() throws IOException {
		return null;
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return LocalDateTime.now();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return null;
	}

	@Override
	public UUID ownerId() throws IOException {
		return null;
	}

	@Override
	public String tag() throws IOException {
		return StringUtils.EMPTY;
	}

	@Override
	public Base base() {
		return base;
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return base.select(clazz);
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return base.select(clazz, viewScript);
	}

}
