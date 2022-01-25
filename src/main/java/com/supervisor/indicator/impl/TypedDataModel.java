package com.supervisor.indicator.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.domain.impl.PxAggregatedModel;
import com.supervisor.domain.impl.PxDataModel;
import com.supervisor.domain.impl.PxDataSheetModel;

public final class TypedDataModel {
	
	private TypedDataModel() {
		
	}
	
	public static DataModel convert(DataModel origin) throws IOException {
		
		final DataModel item;
		
		DataModelType type = origin.type();
		
		switch (type) {
			case DATA_SHEET_MODEL:
				item = new PxDataSheetModel(origin);
				break;
			case AGGREGATED_MODEL:
				item = new PxAggregatedModel(origin);
				break;
			default:
				throw new IllegalArgumentException(String.format("PxDataModels#domainOf: model type %s not supported !", type.toString()));
		}
		
		return item;
	}
	
	public static DataModel convert(final Record<DataModel> record) throws IOException {
		 final DataModel origin = new PxDataModel(record);
		return convert(origin);
	}
}
