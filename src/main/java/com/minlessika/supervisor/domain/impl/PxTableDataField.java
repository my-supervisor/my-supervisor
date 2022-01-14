package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.TableDataField;

public final class PxTableDataField extends PxEditableDataField implements TableDataField {
	
	private final Record<TableDataField> tableRecord;
	
	public PxTableDataField(final Record<DataField> record) throws IOException {
		super(record);
		
		this.tableRecord = record.listOf(TableDataField.class).get(record.id());
	}
	
	public PxTableDataField(final DataSheetModel model) throws IOException {
		this(tableOf(model));
	}
	
	private static final Record<DataField> tableOf(final DataSheetModel model) throws IOException {
		final Record<TableDataField> tableRecord = model.listOf(TableDataField.class)
													    .where(TableDataField::structure, model.id())
													    .first();
		return tableRecord.of(DataField.class, tableRecord.id());
	}

	
	@Override
	protected void checkStyle(Record<DataField> record) throws IOException {
		if(record.valueOf(DataField::type) != DataFieldType.TABLE)
			throw new IllegalArgumentException("This field is not a table !");
	}

	@Override
	public DataSheetModel structure() throws IOException {
		return new PxDataSheetModel(tableRecord.of(TableDataField::structure));
	}

	@Override
	public List<EditableDataField> columns() throws IOException {
		return structure().fields().editables().items();
	}
	
	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {
		throw new UnsupportedOperationException("FormularDataField::update");
	}

	@Override
	public void update(String code, String name, String description) throws IOException {
		super.update(code, name, DataFieldType.TABLE, DataFieldStyle.STRUCTURE, description);
	}
}
