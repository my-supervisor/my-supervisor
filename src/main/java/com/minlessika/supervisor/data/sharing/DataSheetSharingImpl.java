package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.impl.PxDataSheet;

public final class DataSheetSharingImpl extends DataSharingBase<DataSheet, DataSheetShared> implements DataSheetSharing {

	public DataSheetSharingImpl(final DataSheet concrete) {
		super(DataSheet.class, DataSheetShared.class, concrete);
	}
	
	public DataSheetSharingImpl(final DataSheet template, final Activity concreteActivity) {
		super(DataSheet.class, DataSheetShared.class, template, concreteActivity);
	}

	@Override
	public DataSheet concrete() throws IOException {
		return new PxDataSheet(concreteRecord());
	}

	@Override
	public DataSheet template() throws IOException {
		return new PxDataSheet(templateRecord());
	}
}
