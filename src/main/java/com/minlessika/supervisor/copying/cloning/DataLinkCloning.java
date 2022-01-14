package com.minlessika.supervisor.copying.cloning;

import java.io.IOException;

import com.minlessika.supervisor.copying.AbstractDataLinkWriter;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.indicator.Indicator;

public final class DataLinkCloning extends AbstractDataLinkWriter {

	public DataLinkCloning(DataLink source, DataLink target) throws IOException {
		super(source, target);
	}

	public DataLinkCloning(final Indicator targetIndicator, final DataModel targetModel, final DataLink source) {
		super(targetIndicator, targetModel, source);
	}
	
	@Override
	public DataLink copy() throws IOException {
		final DataLink copy = super.copy();
		copy.activate(source.active());
		return copy;
	}
}
