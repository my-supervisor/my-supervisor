package com.supervisor.domain.bi.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.sdk.utils.ListOfUniqueRecord;
import com.supervisor.domain.DataDomainDefinition;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Params;
import com.supervisor.domain.bi.BiRequest;
import com.supervisor.domain.bi.BiSelect;
import com.supervisor.domain.bi.BiSelects;

public final class BiSelectsImpl implements BiSelects {

	private final BiRequest request;
	private final List<BiSelect> items;
	
	public BiSelectsImpl(final BiRequest request) {
		this.request = request;
		this.items = new ArrayList<>();
	}
	
	@Override
	public List<BiSelect> items() {
		return items;
	}

	@Override
	public BiSelect add(DataModel view, Map<DataField, DataLinkOperator> columns, List<DataField> searchableColumns, Params params, DataDomainDefinition dataDomainDefinition, final Ordering ordering, final long start, final int limit) throws IOException {
		
		final List<DataField> columnsStamp = new ListOfUniqueRecord<>();
		final Map<DataField, DataLinkOperator> filteredColumns = new LinkedHashMap<>();
		for (Entry<DataField, DataLinkOperator> entry : columns.entrySet()) {
			if(!columnsStamp.contains(entry.getKey())) {
				columnsStamp.add(entry.getKey());
				filteredColumns.put(entry.getKey(), entry.getValue());
			}
		}
		
		final List<DataField> filteredSearchableColumns = new ListOfUniqueRecord<>(searchableColumns);
		final BiSelect item = new BiSelectImpl(request, view, filteredColumns, filteredSearchableColumns, params, dataDomainDefinition, ordering, start, limit);
		items.add(item);
		
		return item;
	}

}
