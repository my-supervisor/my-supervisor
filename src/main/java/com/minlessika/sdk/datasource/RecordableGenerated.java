package com.minlessika.sdk.datasource;

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
	public Long id() {
		return 0L;
	}

	@Override
	public UUID guid() throws IOException {
		return UUID.randomUUID();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return LocalDateTime.now();
	}

	@Override
	public Long creatorId() throws IOException {
		return 0L;
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return LocalDateTime.now();
	}

	@Override
	public Long lastModifierId() throws IOException {
		return 0L;
	}

	@Override
	public Long ownerId() throws IOException {
		return 0L;
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
