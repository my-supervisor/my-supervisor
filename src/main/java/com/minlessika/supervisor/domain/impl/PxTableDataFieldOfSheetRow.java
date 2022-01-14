package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.TableDataFieldOfSheet;
import com.minlessika.supervisor.domain.TableDataFieldOfSheetRow;

public final class PxTableDataFieldOfSheetRow extends DataSheetWrap implements TableDataFieldOfSheetRow {

	private final Record<TableDataFieldOfSheetRow> record;
	
	public PxTableDataFieldOfSheetRow(Record<TableDataFieldOfSheetRow> record) throws IOException {
		super(sheetOf(record));		
		
		if(record.valueOf(TableDataFieldOfSheetRow::table) == null)
			throw new IllegalArgumentException("PxTableDataFieldOfSheetRow: This data sheet is not a row !");
		
		this.record = record;
	}
	
	private static DataSheet sheetOf(Record<TableDataFieldOfSheetRow> record) throws IOException {
		return new PxDataSheet(record.listOf(DataSheet.class).get(record.id()));
	}
	
	public PxTableDataFieldOfSheetRow(DataSheet origin) throws IOException {
		super(origin);
		
		this.record = origin.listOf(TableDataFieldOfSheetRow.class).get(origin.id());
		
		if(record.valueOf(TableDataFieldOfSheetRow::table) == null)
			throw new IllegalArgumentException("PxTableDataFieldOfSheetRow: This data sheet is not a row !");
	}

	@Override
	public TableDataFieldOfSheet table() throws IOException {
		final DataFieldOfSheet sheet = new PxDataFieldOfSheet(record.of(TableDataFieldOfSheetRow::table));
		return new PxTableDataFieldOfSheet(sheet);
	}

}
