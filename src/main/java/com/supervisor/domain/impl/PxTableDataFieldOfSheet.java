package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRows;

public final class PxTableDataFieldOfSheet extends DataFieldOfSheetWrap implements TableDataFieldOfSheet {
	
	private final TableDataField field;
	
	public PxTableDataFieldOfSheet(final DataFieldOfSheet origin) throws IOException {
		super(origin);
		
		this.field = (TableDataField)origin.origin();
	}

	@Override
	public TableDataFieldOfSheetRows rows() throws IOException {
		return new PxTableDataFieldOfSheetRows(this);
	}

	@Override
	public DataSheetModel structure() throws IOException {
		return field.structure();
	}

	@Override
	public List<EditableDataField> columns() throws IOException {
		return field.columns();
	}

	@Override
	public void update(String code, String name, String description) throws IOException {
		field.update(code, name, description); 
	}
}
