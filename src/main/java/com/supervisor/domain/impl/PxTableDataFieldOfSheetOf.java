package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ListDataFieldOfSheet;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRows;

public final class PxTableDataFieldOfSheetOf extends DataFieldOfSheetWrap implements TableDataFieldOfSheet {
	
	private final TableDataFieldOfSheet origin;
	
	public PxTableDataFieldOfSheetOf(final ListDataFieldOfSheet cell) throws IOException {
		super(tableOf(cell));
		
		origin = tableOf(cell);
	}

	private static final TableDataFieldOfSheet tableOf(final ListDataFieldOfSheet cell) throws IOException {
		
		final DataSheetModel model = new PxDataSheetModel(cell.model());
		final TableDataField field = new PxTableDataField(model);
		final DataSheet sheet = cell.sheetToRefer();
		
		return new PxTableDataFieldOfSheet(
					new PxDataFieldOfSheet(
						cell.listOf(DataFieldOfSheet.class)
							.where(DataFieldOfSheet::origin, field.id())
							.where(DataFieldOfSheet::sheet, sheet.id())
							.first()
					)
			   );
	}

	@Override
	public DataSheetModel structure() throws IOException {
		return origin.structure();
	}

	@Override
	public List<EditableDataField> columns() throws IOException {
		return origin.columns();
	}

	@Override
	public TableDataFieldOfSheetRows rows() throws IOException {
		return origin.rows();
	}
	
	@Override
	public void update(String code, String name, String description) throws IOException {
		origin.update(code, name, description); 
	}
}
