package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.sdk.utils.ListOfUniqueRecord;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelDependencies;

public final class DataModelsThatAreDependenciesOf implements DataModelDependencies {

	private final DataModel model;
	private final boolean strictly;
	
	public DataModelsThatAreDependenciesOf(final DataModel model, final boolean strictly) {
		this.model = model;
		this.strictly = strictly;
	}
	
	@Override
	public List<DataModel> items() throws IOException {
		final List<DataModel> items = new ListOfUniqueRecord<>();
		final List<DataModel> userDataModels = new PgUserDataModels(new OwnerOf(model)).where(DataModel::isTemplate, model.isTemplate()).items();
		
		for (DataModel dataModel : userDataModels) {
			if(strictly && model.strictDependsOn(dataModel) || !strictly && model.dependsOn(dataModel)) {
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
