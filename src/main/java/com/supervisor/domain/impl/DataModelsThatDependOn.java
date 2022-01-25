package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.utils.ListOfUniqueRecord;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelDependencies;

public final class DataModelsThatDependOn implements DataModelDependencies {

	private final DataModel model;
	private final boolean strictly;
	
	public DataModelsThatDependOn(final DataModel model, final boolean strictly) {
		this.model = model;
		this.strictly = strictly;
	}
	
	@Override
	public List<DataModel> items() throws IOException {
		
		final List<DataModel> items = new ListOfUniqueRecord<>();
		final List<DataModel> userDataModels = new PgUserDataModels(new OwnerOf(model)).where(DataModel::isTemplate, model.isTemplate()).items();
		
		for (DataModel dataModel : userDataModels) {
			if(strictly && dataModel.strictDependsOn(model) || !strictly && dataModel.dependsOn(model)) {
				items.add(dataModel);
			}
		}
		
		return items;
	}
	
	@Override
	public boolean contains(DataModel model) throws IOException {
		for (DataModel m : items()) {
			if(m.id().equals(model.id()))
				return true;
		}
		
		return false;
	}

}
