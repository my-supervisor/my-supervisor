package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.TableDataFieldOfSheet;
import com.minlessika.supervisor.domain.TableDataFieldOfSheetRows;

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
