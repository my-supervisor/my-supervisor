package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldGroup;
import com.supervisor.domain.DataFieldSorter;

public final class DataFieldSortingOnDependency implements DataFieldSorter {

	private final List<DataField> itemsToSort;
	
	public DataFieldSortingOnDependency(final List<DataField> itemsToSort) {
		this.itemsToSort = itemsToSort;
	}
	
	@Override
	public List<DataField> items() throws IOException {
		final List<DataField> items = new ArrayList<>();
		
		for (DataFieldGroup group : groups()) {
			items.addAll(group.items());
		}
		
		return items;
	}

	@Override
	public List<DataFieldGroup> groups() throws IOException {
		
		final List<DataFieldGroup> groups = new ArrayList<>();
		final List<DataField> dataFields = itemsToSort.stream().collect(Collectors.toList());
		
		// level 0
		final List<DataField> dataFields0 = dataFields.stream().collect(Collectors.toList());
		final List<DataField> itemsOfGroup0 = new ArrayList<>();
		for (DataField dataField : dataFields0) {
			if(dataField.dependencies().isEmpty()) {
				itemsOfGroup0.add(dataField);
				dataFields.remove(dataField);
			}
		}
		
		final DataFieldGroup group0 = new DataFieldGroupImpl(0, itemsOfGroup0);
		groups.add(group0);
		
		// level i
		int i = 1;
		do {
			final List<DataField> dataFieldsi = dataFields.stream().collect(Collectors.toList());
			final List<DataField> itemsOfGroupi = new ArrayList<>();
			
			for (DataField dataField : dataFieldsi) {
				
				boolean useDataField = false;	
				
				for (DataField fu : dataField.dependencies().items()) {
					if(itemsOfGroupi.stream().anyMatch(c -> c.id().equals(fu.id())) || dataFields.stream().anyMatch(c -> c.id().equals(fu.id()))) {
						useDataField = true;
						break;
					}
				}
				
				if(!useDataField) {
					itemsOfGroupi.add(dataField);
					dataFields.remove(dataField);
				}
			}
			
			final DataFieldGroup groupi = new DataFieldGroupImpl(i, itemsOfGroupi);
			groups.add(groupi);
			
			i++;
		} while (!dataFields.isEmpty());
		
		return groups;
	}

}
