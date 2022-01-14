package com.minlessika.supervisor.copying.generating;

import java.io.IOException;

import com.minlessika.supervisor.copying.AbstractDataLinkWriter;
import com.minlessika.supervisor.data.sharing.DataLinkSharingImpl;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.indicator.Indicator;

public final class DataLinkGenerating extends AbstractDataLinkWriter {

	public DataLinkGenerating(DataLink source, DataLink target) throws IOException {
		super(source, target);
	}

	public DataLinkGenerating(final Indicator targetIndicator, final DataModel targetModel, final DataLink source) {
		super(targetIndicator, targetModel, source);
	}
	
	@Override
	public DataLink copy() throws IOException {
		final DataLink copy = super.copy();
		if(target == DataLink.EMPTY) {
			if(copy.interacting()) {
				final DataModel linkModel = copy.model();
				final Activity targetActivity = targetIndicator.activity();
				if(linkModel.interacting() && !targetActivity.interactsWith(((AggregatedModel)linkModel).coreActivity())) {
					copy.activate(false);
				} else if(!targetActivity.interactsWith(linkModel.activity())) {
					copy.activate(false);
				}							
			}			
		}
		new DataLinkSharingImpl(source, copy.activity()).linkTo(copy);
		return copy;
	}
}
