package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.impl.PxDataLink;

public final class DataLinkSharingImpl extends DataSharingBase<DataLink, DataLinkShared> implements DataLinkSharing {

	public DataLinkSharingImpl(final DataLink concrete) {
		super(DataLink.class, DataLinkShared.class, concrete);
	}
	
	public DataLinkSharingImpl(final DataLink template, final Activity concreteActivity) {
		super(DataLink.class, DataLinkShared.class, template, concreteActivity);
	}

	@Override
	public DataLink concrete() throws IOException {
		return new PxDataLink(concreteRecord());
	}

	@Override
	public DataLink template() throws IOException {
		return new PxDataLink(templateRecord());
	}
}
