package com.minlessika.sdk.tests.utils;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class FkRecordable implements Recordable {

	private final Long id;
	
	public FkRecordable(Long id) {
		this.id = id;
	}
	
	@Override
	public Long id() {
		return id;
	}

	@Override
	public UUID guid() throws IOException {
		
		return null;
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		
		return null;
	}

	@Override
	public Long creatorId() throws IOException {
		
		return null;
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		
		return null;
	}

	@Override
	public Long lastModifierId() throws IOException {
		
		return null;
	}

	@Override
	public Long ownerId() throws IOException {
		
		return null;
	}

	@Override
	public String tag() throws IOException {
		
		return null;
	}

	@Override
	public Base base() {
		
		return null;
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		
		return null;
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		
		return null;
	}

}
