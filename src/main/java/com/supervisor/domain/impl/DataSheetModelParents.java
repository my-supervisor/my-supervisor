package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.utils.ListOfUniqueRecord;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.ExtendedDataSheetModels;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;

public final class DataSheetModelParents implements ExtendedDataSheetModels {

	private final DataSheetModel model;
	
	public DataSheetModelParents(final DataSheetModel model) {
		this.model = model;
	}
	
	@Override
	public List<DataSheetModel> items() throws IOException {
		final List<DataSheetModel> items = new ListOfUniqueRecord<>();
		
		for (ListDataField list : model.fields().lists().items()) {
			for (ListDataFieldSource src : list.sources().items()) {
				final DataModel srcModel = src.model();
				if(srcModel.type() == DataModelType.DATA_SHEET_MODEL) {
					items.add(new PxDataSheetModel(srcModel));
				} else {
					items.add(new PxAggregatedModel(srcModel).coreModel());
				}			
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
		
		throw new IllegalArgumentException(String.format("Model %s don't have a parent model with id %s !", model.name(), id));
	}

}
