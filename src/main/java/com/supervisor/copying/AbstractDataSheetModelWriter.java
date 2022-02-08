package com.supervisor.copying;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.domain.Activity;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Writer;

public abstract class AbstractDataSheetModelWriter implements Writer<DataSheetModel> {

	protected final Activity targetActivity;
	protected final DataSheetModel source;
	protected final DataSheetModel target;
	protected final Map<UUID, DataModel> dataModelMappings;
	
	public AbstractDataSheetModelWriter(final Activity targetActivity, final DataSheetModel source, final Map<UUID, DataModel> dataModelMappings) {
		this(targetActivity, source, DataSheetModel.EMPTY, dataModelMappings);
	}
	
	public AbstractDataSheetModelWriter(final DataSheetModel source, final DataSheetModel target, final Map<UUID, DataModel> dataModelMappings) throws IOException {
		this(target.activity(), source, target, dataModelMappings);
	}
	
	private AbstractDataSheetModelWriter(final Activity targetActivity, final DataSheetModel source, final DataSheetModel target, final Map<UUID, DataModel> dataModelMappings) {
		this.targetActivity = targetActivity;
		this.source = source;
		this.target = target;
		this.dataModelMappings = dataModelMappings;
	}

	protected DataSheetModel copyBaseOf(final DataSheetModel source) throws IOException {
		
		final DataSheetModel copy;
		
		if(target == DataSheetModel.EMPTY) {
			copy = targetActivity.forms()
					             .add(
										source.code(), 
										source.name(), 
										source.description()
								  );						
		} else {
			copy = target;			
		}
		
		copy.update(copy.code(), source.name(), source.description());
		copy.merging(source.canMergeAtSameDate());
		
		return copy;
	}
	
	@Override
	public DataSheetModel copy() throws IOException {
		return copyBaseOf(source);
	}
	
}
