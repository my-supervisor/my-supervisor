package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelGroup;
import com.supervisor.domain.DataModelSorter;

public final class DataModelSortingOnDependency implements DataModelSorter {

	private final List<DataModel> itemsToSort;
	private final boolean strictly;
	
	public DataModelSortingOnDependency(final List<DataModel> itemsToSort, final boolean strictly) {
		this.itemsToSort = itemsToSort;
		this.strictly = strictly;
	}
	
	@Override
	public List<DataModel> items() throws IOException {
		final List<DataModel> items = new ArrayList<>();
		
		for (DataModelGroup group : groups()) {
			items.addAll(group.items());
		}
		
		return items;
	}

	@Override
	public List<DataModelGroup> groups() throws IOException {
		
		final List<DataModelGroup> groups = new ArrayList<>();
		final List<DataModel> models = itemsToSort.stream().collect(Collectors.toList());
		
		// level 0
		final List<DataModel> models0 = models.stream().collect(Collectors.toList());
		final List<DataModel> itemsOfGroup0 = new ArrayList<>();
		for (DataModel model : models0) {
			if(new DataModelsThatAreDependenciesOf(model, strictly).items().isEmpty()) {
				itemsOfGroup0.add(model);
				models.remove(model);
			}
		}
		
		final DataModelGroup group0 = new DataModelGroupImpl(0, itemsOfGroup0);
		groups.add(group0);
		
		// level i
		int i = 1;
		do {
			final List<DataModel> modelsi = models.stream().collect(Collectors.toList());
			final List<DataModel> itemsOfGroupi = new ArrayList<>();
			
			for (DataModel model : modelsi) {
				
				boolean useModel = false;	
				
				for (DataModel fu : new DataModelsThatAreDependenciesOf(model, strictly).items()) {
					if(itemsOfGroupi.stream().anyMatch(c -> c.id().equals(fu.id())) || models.stream().anyMatch(c -> c.id().equals(fu.id()))) {
						useModel = true;
						break;
					}
				}
				
				if(!useModel) {
					itemsOfGroupi.add(model);
					models.remove(model);
				}
			}
			
			final DataModelGroup groupi = new DataModelGroupImpl(i, itemsOfGroupi);
			groups.add(groupi);
			
			i++;
		} while (!models.isEmpty());
		
		return groups;
	}

}
