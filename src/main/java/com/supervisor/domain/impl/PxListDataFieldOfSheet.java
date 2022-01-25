package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.domain.bi.BiResult;
import com.supervisor.sdk.datasource.Record;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRow;

public final class PxListDataFieldOfSheet extends DataFieldOfSheetWrap implements ListDataFieldOfSheet {

	private final Record<ListDataFieldOfSheet> record;
	
	public PxListDataFieldOfSheet(DataFieldOfSheet origin) throws IOException {
		super(origin);
		
		if(origin.style() != DataFieldStyle.LIST)
			throw new IllegalArgumentException("Data is not list type !");
		
		this.record = origin.listOf(ListDataFieldOfSheet.class).get(origin.id());
	}
	
	public PxListDataFieldOfSheet(Record<ListDataFieldOfSheet> record) throws IOException {
		this(fieldOf(record));
	}
	
	private static DataFieldOfSheet fieldOf(Record<ListDataFieldOfSheet> record) throws IOException {
		return new PxDataFieldOfSheet(
					record.listOf(DataFieldOfSheet.class)
						  .get(record.id())
			   );
	}

	@Override
	public DataSheet sheetToRefer() throws IOException {
		if(record.valueOf(ListDataFieldOfSheet::sheetToRefer) == null)
			return DataSheet.EMPTY;
		
		return new PxDataSheet(record.of(ListDataFieldOfSheet::sheetToRefer)); 
	}

	@Override
	public void update() throws IOException {
		final ListDataField origin = (ListDataField)origin();
		final BiResult result = origin.value(sheetToRefer().reference());
		if(result.rows().isEmpty())
			throw new IllegalArgumentException(String.format("Sheet with reference %s not found !", sheetToRefer().reference()));
		
		final String value = result.rows().get(0).get(origin.fieldToDisplay().code()).value().toString();
		update(value);
	}
	
	@Override
	public void update(String reference, String value) throws IOException {
		
		if(StringUtils.isBlank(reference) && origin().isMandatory())
			throw new IllegalArgumentException(String.format("%s is mandatory !", name()));
		
		if(StringUtils.isBlank(reference)) {
			
			record.entryOf(ListDataFieldOfSheet::sheetToRefer, null)
		  	      .update();
			
			origin.update(null);
			
			// check if it is a sub field PARENT
			if(code().equals("PARENT")) {
				record.listOf(TableDataFieldOfSheetRow.class)
					  .get(sheet().id())
				      .entryOf(TableDataFieldOfSheetRow::table, null)
				      .update();
			}
		} else {
			final DataSheets sheets = new PgDataSheets(new OwnerOf(model())).where(DataSheet::reference, reference);
			if(sheets.isEmpty())
				throw new IllegalArgumentException(String.format("Sheet with reference %s not found !", reference));
			
			final DataSheet sheetToRefer = sheets.last();
			
			update(sheetToRefer, value);
		}	
	}

	@Override
	public void update(DataSheet sheetToRefer, String value) throws IOException {
		
		if(sheetToRefer == DataSheet.EMPTY && origin().isMandatory())
			throw new IllegalArgumentException(String.format("%s is mandatory !", name()));
					
		if(sheetToRefer == DataSheet.EMPTY) {
			record.entryOf(ListDataFieldOfSheet::sheetToRefer, null)
		  	  .update();
		
			origin.update(null);
			
			// check if it is a sub field PARENT
			if(code().equals("PARENT")){
				record.listOf(TableDataFieldOfSheetRow.class)
					  .get(sheet().id())
				      .entryOf(TableDataFieldOfSheetRow::table, null)
				      .update();
			}
		} else {
			final ListDataField list = (ListDataField)origin();
			if(!list.isBasedOn(sheetToRefer.model()))
				throw new IllegalArgumentException(String.format("Model (%s) of data sheet doesn't belong to list source models !", sheetToRefer.model().name()));
			
			record.entryOf(ListDataFieldOfSheet::sheetToRefer, sheetToRefer.id())
		  	      .update();
		
			origin.update(value);
			
			// check if it is a sub field PARENT
			if(code().equals("PARENT")){
				final TableDataFieldOfSheet table = new PxTableDataFieldOfSheetOf(this);
				record.listOf(TableDataFieldOfSheetRow.class)
					  .get(sheet().id())
				      .entryOf(TableDataFieldOfSheetRow::table, table.id())
				      .update();
			}
		}		
	}

}
