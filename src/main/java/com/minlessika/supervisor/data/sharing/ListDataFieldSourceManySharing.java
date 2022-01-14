package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.domain.impl.PxListDataFieldSource;

public final class ListDataFieldSourceManySharing extends DataSharingBase<ListDataFieldSource, ListDataFieldSourceShared> implements ListDataFieldSourceSharing {

	public ListDataFieldSourceManySharing(final ListDataFieldSource concrete) {
		super(ListDataFieldSource.class, ListDataFieldSourceShared.class, concrete);
	}
	
	public ListDataFieldSourceManySharing(final ListDataFieldSource template, final Activity concreteActivity) {
		super(ListDataFieldSource.class, ListDataFieldSourceShared.class, template, concreteActivity);
	}

	@Override
	public ListDataFieldSource concrete() throws IOException {
		return new PxListDataFieldSource(concreteRecord());
	}

	@Override
	public ListDataFieldSource template() throws IOException {
		return new PxListDataFieldSource(templateRecord());
	}
}
