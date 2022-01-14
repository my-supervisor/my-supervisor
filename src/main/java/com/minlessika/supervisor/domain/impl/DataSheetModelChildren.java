package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.sdk.utils.ListOfUniqueRecord;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.ExtendedDataSheetModels;

public final class DataSheetModelChildren implements ExtendedDataSheetModels {

	private final DataSheetModel model;
	
	public DataSheetModelChildren(final DataSheetModel model) {
		this.model = model;
	}
	
	@Override
	public List<DataSheetModel> items() throws IOException {
		final List<DataSheetModel> items = new ListOfUniqueRecord<>();
		for (DataSheetModel m : new PgDataSheetModels(new OwnerOf(model)).where(DataSheetModel::ownerId, model.ownerId()).items()) {
			if(m.parents().contains(model)) {
				items.add(m);
			}
		}		
		
		return items;
	}

	@Override
	public boolean contains(DataSheetModel item) throws IOException {
		
		for (DataModel m : items()) {
			if(m.id().equals(item.id())) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public DataSheetModel get(Long id) throws IOException {
		for (DataSheetModel m : items()) {
			if(m.id().equals(id)) {
				return m;
			}
		}	
		
		throw new IllegalArgumentException(String.format("Model %s don't have a child model with id %s !", model.name(), id));
	}

}
