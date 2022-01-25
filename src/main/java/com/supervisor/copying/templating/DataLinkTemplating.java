package com.supervisor.copying.templating;

import java.io.IOException;

import com.supervisor.copying.AbstractDataLinkWriter;
import com.supervisor.sharing.DataLinkSharingImpl;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataModel;
import com.supervisor.indicator.Indicator;

public final class DataLinkTemplating extends AbstractDataLinkWriter {

	public DataLinkTemplating(DataLink source, DataLink target) throws IOException {
		super(source, target);
	}

	public DataLinkTemplating(final Indicator targetIndicator, final DataModel targetModel, final DataLink source) {
		super(targetIndicator, targetModel, source);
	}
	
	@Override
	public DataLink copy() throws IOException {
		final DataLink copy = super.copy();
		
		if(copy.interacting()) {
			copy.activate(false);
		}
		
		new DataLinkSharingImpl(source).linkTo(copy);
		return copy;
	}
}
