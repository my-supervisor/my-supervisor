package com.minlessika.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractDataFieldOfSheetWriter;
import com.minlessika.supervisor.copying.generating.DataSheetGenerating;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.ListDataFieldOfSheet;
import com.minlessika.supervisor.domain.TableDataFieldOfSheet;
import com.minlessika.supervisor.domain.TableDataFieldOfSheetRow;

public final class DataFieldOfSheetCloning extends AbstractDataFieldOfSheetWriter {

	public DataFieldOfSheetCloning(DataFieldOfSheet source, DataFieldOfSheet target, Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}

	public DataFieldOfSheetCloning(final DataSheet targetSheet, final DataFieldOfSheet source, final Map<Long, DataModel> dataModelMappings) {
		super(targetSheet, source, dataModelMappings);
	}

	@Override
	protected DataSheet copyListDataSheet(DataSheetModel copy, DataSheet source) throws IOException {
		
		final DataSheet targetSheetList;
		
		if(target == DataFieldOfSheet.EMPTY) {
			targetSheetList = new DataSheetCloning(copy, source, dataModelMappings).copy();
		} else {
			final ListDataFieldOfSheet targetListOfSheet = (ListDataFieldOfSheet)target;
			targetSheetList = new DataSheetCloning(source, targetListOfSheet.sheetToRefer(), dataModelMappings).copy();
		}
		
		return targetSheetList;
	}

	@Override
	protected TableDataFieldOfSheetRow copyTableRow(TableDataFieldOfSheet copy, TableDataFieldOfSheetRow source) throws IOException {	
		final TableDataFieldOfSheetRow newRow = copy.rows().add();
		new DataSheetGenerating(source, newRow, dataModelMappings).copy();		
		return newRow;
	}
}
