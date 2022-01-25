package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataField;
import com.supervisor.domain.MappedDataField;
import com.supervisor.indicator.IndicatorDynamicParam;

public final class GeneratedMappedDataField implements MappedDataField {

	private final IndicatorDynamicParam origin;
	private DataField fieldToUse;
	private DataLinkOperator operator;
	
	public GeneratedMappedDataField(final IndicatorDynamicParam origin) {
		this(origin, DataLinkOperator.NONE, DataField.EMPTY);
	}
	
	public GeneratedMappedDataField(final IndicatorDynamicParam origin, final DataLinkOperator operator, final DataField fieldToUse) {
		this.origin = origin;
		this.operator = operator;
		this.fieldToUse = fieldToUse;
	}

	@Override
	public Long id() {
		return 0L;
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
	public DataLink link() throws IOException {
		return DataLink.EMPTY;
	}

	@Override
	public DataField fieldToUse() throws IOException {
		return fieldToUse;
	}

	@Override
	public IndicatorDynamicParam param() throws IOException {
		return origin;
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
	public void update(DataLinkOperator operator, DataField field) throws IOException {
		this.operator = operator;
		fieldToUse = field;
	}
	
	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return origin.listOf(clazz, viewScript);
	}

	@Override
	public DataLinkOperator operator() throws IOException {
		return operator;
	}
}
