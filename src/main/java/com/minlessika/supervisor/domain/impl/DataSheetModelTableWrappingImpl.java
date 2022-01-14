package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModelTableWrapping;
import com.minlessika.supervisor.domain.TableDataField;

public final class DataSheetModelTableWrappingImpl implements DataSheetModelTableWrapping  {

	private final DataModel model;
	
	public DataSheetModelTableWrappingImpl(final DataModel model) {
		this.model = model;
	}
	
	@Override
	public boolean isStructure() throws IOException {
		return model.listOf(TableDataField.class)
				    .where(TableDataField::structure, model.id())
				    .any();
	}

}
