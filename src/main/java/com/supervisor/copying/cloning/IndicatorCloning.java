package com.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.copying.AbstractIndicatorWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataModel;
import com.supervisor.indicator.Indicator;

public final class IndicatorCloning extends AbstractIndicatorWriter {

	public IndicatorCloning(Activity targetActivity, Indicator source, Map<UUID, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}

	public IndicatorCloning(final Indicator source, final Indicator target, final Map<UUID, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}

	@Override
	protected DataLink copyDataLink(final Indicator copy, final DataModel targetModel, final DataLink source) throws IOException {
		return new DataLinkCloning(copy, targetModel, source).copy();
	}

	@Override
	protected void copyDataLink(DataLink source, DataLink target) throws IOException {
		new DataLinkCloning(source, target).copy();
	}

	@Override
	protected void copyDataLinksTo(Indicator copy) throws IOException {
		
		// remove all links
		copy.links().remove();
		
		for (DataLink link : source.links().items()) {
			
			final DataModel linkDataModel = link.model();
			final DataModel targetDataModel = dataModelMappings.get(linkDataModel.id());
			copyDataLink(copy, targetDataModel, link);							
		}
	}
}
