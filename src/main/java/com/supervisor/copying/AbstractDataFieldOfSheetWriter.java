package com.supervisor.copying;

import java.io.IOException;
import java.util.Map;

import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldOfSheet;
import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.SimpleDataFieldOfSheet;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRow;
import com.supervisor.domain.Writer;

public abstract class AbstractDataFieldOfSheetWriter implements Writer<DataFieldOfSheet> {

	protected final DataSheet targetSheet;
	protected final DataFieldOfSheet source;
	protected final DataFieldOfSheet target;
	protected final Map<Long, DataModel> dataModelMappings;
	
	public AbstractDataFieldOfSheetWriter(final DataSheet targetSheet, final DataFieldOfSheet source, final Map<Long, DataModel> dataModelMappings) {
		this(targetSheet, source, DataFieldOfSheet.EMPTY, dataModelMappings);
	}
	
	public AbstractDataFieldOfSheetWriter(final DataFieldOfSheet source, final DataFieldOfSheet target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		this(target.sheet(), source, target, dataModelMappings);
	}
	
	private AbstractDataFieldOfSheetWriter(final DataSheet targetSheet, final DataFieldOfSheet source, final DataFieldOfSheet target, final Map<Long, DataModel> dataModelMappings) {
		this.targetSheet = targetSheet;
		this.source = source;
		this.target = target;
		this.dataModelMappings = dataModelMappings;
	}

	protected abstract DataSheet copyListDataSheet(final DataSheetModel copy, final DataSheet source) throws IOException;
	
	protected abstract TableDataFieldOfSheetRow copyTableRow(TableDataFieldOfSheet copy, final TableDataFieldOfSheetRow source) throws IOException;
	
	@Override
	public DataFieldOfSheet copy() throws IOException {
		
		final DataFieldOfSheet copy;
		
		final DataSheetModel sourceModel = source.model();		
		final DataSheetModel targetModel = targetSheet.model();	
		
		switch (source.style()) {
			case SIMPLE:				
				final String value;
				if(source.code().equals("REF")) {
					value = targetSheet.reference();
				} else {
					value = source.value();
				}
				if(target == DataFieldOfSheet.EMPTY) {
					final SimpleDataField simple = targetModel.fields().simples().get(source.code());
					copy = targetSheet.fields().add(simple, value);
				} else {
					final SimpleDataFieldOfSheet simple = (SimpleDataFieldOfSheet)targetSheet.fields().get(source.code());
					simple.update(value);
					copy = simple;
				}				
				break;
			case LIST:
				final ListDataFieldOfSheet sourceListOfSheet = (ListDataFieldOfSheet)source;
				final DataSheetModel sourceListModel = sourceListOfSheet.sheetToRefer().model();
				final DataSheet sourceListSheet = sourceListOfSheet.sheetToRefer();
				final ListDataField sourceList = targetModel.fields().lists().get(source.code());
				
				final DataSheetModel targetListModel;
				if(sourceModel.activity().id().equals(sourceListModel.activity().id())) {
					// data sheet model provided by current activity
					targetListModel = (DataSheetModel)dataModelMappings.get(sourceListModel.id());						 
				} else {
					// interaction
					throw new IllegalArgumentException("List field interaction is not supported !");
				}
				
				final DataSheet targetSheetList = copyListDataSheet(targetListModel, sourceListSheet);
				
				final String listValue;
				if(sourceList.fieldToDisplay().code().equals("REF")) {
					listValue = targetSheetList.reference();
				} else {
					listValue = sourceListOfSheet.value();
				}
				
				if(target == DataFieldOfSheet.EMPTY) {
					copy = targetSheet.fields().add(sourceList, targetSheetList, listValue);
				} else {
					final ListDataFieldOfSheet targetListOfSheet = (ListDataFieldOfSheet)target;
					targetListOfSheet.update(targetSheetList, listValue); 
					copy = targetListOfSheet;
				}				
				break;	
			case STRUCTURE:
				final TableDataFieldOfSheet sourceOfSheetTable = (TableDataFieldOfSheet)source;
				final TableDataField table = targetSheet.model().fields().tables().get(source.code());
				if(target == DataFieldOfSheet.EMPTY) {
					copy = targetSheet.fields().add(table);
				} else {
					copy = target;
				}
				
				final TableDataFieldOfSheet targetOfSheetTable = (TableDataFieldOfSheet)copy;
				for (TableDataFieldOfSheetRow row : sourceOfSheetTable.rows().items()) {					
					copyTableRow(targetOfSheetTable, row);
				}
				break;
			default:
				throw new IllegalArgumentException(String.format("DataFieldOfSheetWriter#copy: Data field style %s not supported !", source.style().toString())); 
		}
		
		return copy;
	}
	
}
