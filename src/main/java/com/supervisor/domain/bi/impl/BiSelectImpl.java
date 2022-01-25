package com.supervisor.domain.bi.impl;

import java.util.List;
import java.util.Map;

import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.domain.DataDomainDefinition;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Params;
import com.supervisor.domain.bi.BiRequest;
import com.supervisor.domain.bi.BiSelect;

public final class BiSelectImpl implements BiSelect {

	private final BiRequest request;
	private final DataModel view;
	private final Map<DataField, DataLinkOperator> columns;
	private final List<DataField> searchableColumns;
	private final Params params;
	private final DataDomainDefinition dataDomainDefinition;
	private final Ordering ordering;
	private final long start;
	private final int limit;
	
	public BiSelectImpl(final BiRequest request, final DataModel view, final Map<DataField, DataLinkOperator> columns, final List<DataField> searchableColumns, final Params params, final DataDomainDefinition dataDomainDefinition, final Ordering ordering, final long start, final int limit) {
		this.request = request;
		this.view = view;
		this.columns = columns;
		this.searchableColumns = searchableColumns;
		this.params = params;
		this.dataDomainDefinition = dataDomainDefinition;
		this.ordering = ordering;
		this.start = start;
		this.limit = limit;
	}
	
	@Override
	public DataModel view() {
		return view;
	}

	@Override
	public Map<DataField, DataLinkOperator> columns() {
		return columns;
	}

	@Override
	public List<DataField> searchableColumns() {
		return searchableColumns;
	}

	@Override
	public Params params() {
		return params;
	}

	@Override
	public BiRequest request() {
		return request;
	}

	@Override
	public DataDomainDefinition dataDomainDefinition() {
		return dataDomainDefinition;
	}

	@Override
	public Ordering ordering() {
		return ordering;
	}

	@Override
	public long start() {
		return start;
	}

	@Override
	public int limit() {
		return limit;
	}
}
