package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldDependencies;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;

public final class FieldID implements DataField {
	
	@Override
	public Long id() {
		return -2L;
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
		return 1L;
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return LocalDateTime.now();
	}

	@Override
	public Long lastModifierId() throws IOException {
		return 1L;
	}

	@Override
	public Long ownerId() throws IOException {
		return 1L;
	}

	@Override
	public String tag() throws IOException {
		return null;
	}

	@Override
	public Base base() {
		return Base.EMPTY;
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		throw new UnsupportedOperationException("FieldDate#listOf");
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		throw new UnsupportedOperationException("FieldDate#listOf");
	}

	@Override
	public DataModel model() throws IOException {
		return DataModel.EMPTY;
	}

	@Override
	public String code() throws IOException {
		return "id";
	}

	@Override
	public String name() throws IOException {
		return "ID";
	}

	@Override
	public String description() throws IOException {
		return null;
	}

	@Override
	public DataFieldType type() throws IOException {
		return DataFieldType.NUMBER;
	}

	@Override
	public DataFieldStyle style() throws IOException {
		return DataFieldStyle.SIMPLE;
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
			throws IOException {
		throw new UnsupportedOperationException("FieldDate#update");
	}

	@Override
	public DataFieldDependencies dependencies() throws IOException {
		return new NoDataFieldDependencies();
	}

}
