package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModelTableWrapping;
import com.supervisor.domain.TableDataField;

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
