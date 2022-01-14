package com.minlessika.sdk.tests.utils;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long creatorId() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lastModifierId() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long ownerId() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tag() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Base base() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
