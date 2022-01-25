package com.supervisor.copying;

import java.io.IOException;
import java.util.Map;

import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Writer;

public abstract class AbstractDataSheetWriter implements Writer<DataSheet> {

	protected final DataSheetModel targetModel;
	protected final DataSheet source;
	protected final DataSheet target;
	protected final Map<Long, DataModel> dataModelMappings;
	
	public AbstractDataSheetWriter(final DataSheetModel targetModel, final DataSheet source, final Map<Long, DataModel> dataModelMappings) {
		this(targetModel, source, DataSheet.EMPTY, dataModelMappings);
	}
	
	public AbstractDataSheetWriter(final DataSheet source, final DataSheet target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		this(target.model(), source, target, dataModelMappings);
	}
	
	private AbstractDataSheetWriter(final DataSheetModel targetModel, final DataSheet source, final DataSheet target, final Map<Long, DataModel> dataModelMappings) {
		this.targetModel = targetModel;
		this.source = source;
		this.target = target;
		this.dataModelMappings = dataModelMappings;
	}
	
	private DataSheet copyBaseOf(DataSheet source) throws IOException {
		
		final DataSheet copy;
		
		if(target == DataSheet.EMPTY) {
			// créer la nouvelle feuille
			copy = targetModel.sheets().add(source.reference(), source.date());
		} else {
			copy = target;
			copy.update(source.date());
		}
		
		return copy;
	}
	
	protected abstract DataFieldOfSheet copyDataFieldOfSheet(final DataSheet copy, final DataFieldOfSheet source) throws IOException;
	protected abstract DataFieldOfSheet copyDataFieldOfSheet(final DataFieldOfSheet source, final DataFieldOfSheet target) throws IOException;
	
	protected void copyDataFieldOfSheetsTo(DataSheet copy) throws IOException {
		// copier tous les champs
		for (DataFieldOfSheet field : source.fields().items()) {
			if(copy.fields().where(DataFieldOfSheet::code, field.code()).any()) {
				final DataFieldOfSheet newField = copy.fields().get(field.code());
				copyDataFieldOfSheet(field, newField);
			} else {
				copyDataFieldOfSheet(copy, field);
			}
		}
	}
	
	@Override
	public DataSheet copy() throws IOException {
		
		final DataSheet copy = copyBaseOf(source);
		copyDataFieldOfSheetsTo(copy);			
		return copy;
	}

}
