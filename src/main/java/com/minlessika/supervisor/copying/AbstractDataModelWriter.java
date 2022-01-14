package com.minlessika.supervisor.copying;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Writer;

public abstract class AbstractDataModelWriter implements Writer<DataModel> {

	protected final String code;
	protected final Activity targetActivity;
	protected final DataModel source;
	protected final DataModel target;
	protected final Map<Long, DataModel> dataModelMappings;
	
	public AbstractDataModelWriter(final String code, final Activity targetActivity, final DataModel source, final Map<Long, DataModel> dataModelMappings) {
		this(code, targetActivity, source, DataModel.EMPTY, dataModelMappings);
	}
	
	public AbstractDataModelWriter(final Activity targetActivity, final DataModel source, final Map<Long, DataModel> dataModelMappings) {
		this(StringUtils.EMPTY, targetActivity, source, DataModel.EMPTY, dataModelMappings);
	}
	
	public AbstractDataModelWriter(final DataModel source, final DataModel target, final Map<Long, DataModel> dataModelMappings) {
		this(StringUtils.EMPTY, Activity.EMPTY, source, target, dataModelMappings);
	}
	
	private AbstractDataModelWriter(final String code, final Activity targetActivity, final DataModel source, final DataModel target, final Map<Long, DataModel> dataModelMappings) {
		this.code = code;
		this.targetActivity = targetActivity;
		this.source = source;
		this.target = target;
		this.dataModelMappings = dataModelMappings;
	}
	
	protected abstract DataSheetModel copyDataSheetModel(final Activity copy, final DataSheetModel source) throws IOException;
	protected abstract void copyDataSheetModel(final DataSheetModel source, final DataSheetModel target) throws IOException;
	
	protected abstract AggregatedModel copyAggregatedModel(final DataModel copy, final AggregatedModel source) throws IOException;
	protected abstract void copyAggregatedModel(final AggregatedModel source, final AggregatedModel target) throws IOException;
	
	@Override
	public DataModel copy() throws IOException {
		
		final DataModel copy;
		
		if(target == DataModel.EMPTY) {
			switch (source.type()) {
				case DATA_SHEET_MODEL:
					copy = copyDataSheetModel(targetActivity, (DataSheetModel)source);
					break;
				case AGGREGATED_MODEL:
					final DataModel newDataModel = dataModelMappings.get(((AggregatedModel)source).model().id());
					copy = copyAggregatedModel(newDataModel, (AggregatedModel)source);
					break;
				default:
					throw new IllegalArgumentException(String.format("DataModelWriter : model type %s not supported !", source.type().toString()));
			}
		} else {
			copy = target; 
			switch (copy.type()) {
				case DATA_SHEET_MODEL:
					copyDataSheetModel((DataSheetModel)source, (DataSheetModel)copy);
					break;
				case AGGREGATED_MODEL:
					copyAggregatedModel((AggregatedModel)source, (AggregatedModel)copy);
					break;
				default:
					throw new IllegalArgumentException(String.format("DataModelWriter : model type %s not supported !", copy.type().toString()));
			}
		}
	
		return copy;
	}

}
