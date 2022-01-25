package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.utils.ListOfUniqueRecord;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.IndicatorDependencies;
import com.supervisor.indicator.Indicator;

public final class DataSheetIndicatorDependencies implements IndicatorDependencies {

	private final DataSheetModel model;
	
	public DataSheetIndicatorDependencies(final DataSheet sheet) throws IOException {
		this(sheet.model());
	}
	
	public DataSheetIndicatorDependencies(final DataSheetModel model) {
		this.model = model;
	}
	
	@Override
	public List<Indicator> items() throws IOException {
		final List<Indicator> indicators = new ListOfUniqueRecord<>();
		
		indicators.addAll(model.indicatorsThatDependsOn().items());
		
		// indicators dependencies of them that depends on
		for (DataModel dependency : new DataModelsThatDependOn(model, false).items()) {
			indicators.addAll(dependency.indicatorsThatDependsOn().items());
		}
		
		return indicators;
	}

}
