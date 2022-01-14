package com.minlessika.supervisor.copying;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFields;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.SimpleDataField;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.Writer;

public abstract class AbstractEditableDataFieldWriter implements Writer<EditableDataField> {

	protected final EditableDataField source;
	protected final DataSheetModel targetModel;
	protected final Map<Long, DataModel> dataModelMappings;
	
	public AbstractEditableDataFieldWriter(final DataSheetModel targetModel, final EditableDataField source, final Map<Long, DataModel> dataModelMappings) {
		this.source = source;
		this.targetModel = targetModel;
		this.dataModelMappings = dataModelMappings;
	}

	protected abstract void copyDataSheetModel(final DataSheetModel source, final DataSheetModel target) throws IOException;
	protected abstract void copyListDataFieldSources(final ListDataField source, final ListDataField target) throws IOException;
	
	@Override
	public EditableDataField copy() throws IOException {
		final EditableDataField copy;
		final DataFields targetEditables = targetModel.fields();
		
		if(
				targetEditables.where(DataField::code, source.code())
		                       .isEmpty()
		){
			switch (source.style()) {
				case SIMPLE:
					final SimpleDataField sourceSimple = (SimpleDataField)source;
					copy = targetEditables.simples().add(sourceSimple.code(), sourceSimple.name(), sourceSimple.type(), sourceSimple.isMandatory(), sourceSimple.defaultValue(), sourceSimple.description());
					break;
				case LIST:
					final ListDataField sourceList = (ListDataField)source;
					copy = targetEditables.lists().add(sourceList.code(), sourceList.name(), sourceList.type(), sourceList.isMandatory(), sourceList.description());					
					break;
				case STRUCTURE:
					final TableDataField sourceTable = (TableDataField)source;
					copy = targetEditables.tables().add(sourceTable.code(), sourceTable.name(), sourceTable.isMandatory(), sourceTable.description());
					break;
				default:
					throw new IllegalArgumentException(String.format("EditableDataFieldWriter : %s style not supported !", source.style().toString())); 
			}
		} else {
			copy = targetModel.editableFields().get(source.code());						
		}
		
		copy.order(source.order());
		copy.makeMandatory(source.isMandatory());
		copy.changeUserScope(source.userScope());
		
		switch (source.style()) {
			case SIMPLE:
				final SimpleDataField sourceSimple = (SimpleDataField)source;
				final SimpleDataField targetSimple = (SimpleDataField)copy;
				targetSimple.update(sourceSimple.code(), sourceSimple.name(), sourceSimple.type(), sourceSimple.description());
				targetSimple.defaultValue(sourceSimple.defaultValue());
				targetSimple.makeMustEditOnce(sourceSimple.mustEditOnce());
				targetSimple.takeLastValue(sourceSimple.takeLastValue());
				targetSimple.makeReadOnly(sourceSimple.isReadOnly());
				break;
			case LIST:
				final ListDataField sourceList = (ListDataField)source;
				final ListDataField targetList = (ListDataField)copy;
				targetList.update(sourceList.code(), sourceList.name(), sourceList.type(), sourceList.description());
				targetList.update(sourceList.orderDirection(), sourceList.limit());
				targetList.makeMustEditOnce(sourceList.mustEditOnce());
				targetList.makeReadOnly(sourceList.isReadOnly());
				targetList.makeCanBeUpdated(sourceList.canBeUpdated());
				copyListDataFieldSources(sourceList, targetList); 
				break;
			case STRUCTURE:
				final TableDataField sourceTable = (TableDataField)source;
				final TableDataField targetTable = (TableDataField)copy;
				targetTable.update(sourceTable.code(), sourceTable.name(), sourceTable.description());
				
				// register before synchronizing table structure for this operation needs to call to model
				dataModelMappings.put(
					sourceTable.model().id(), 
					targetTable.model()
				);
				
				copyDataSheetModel(sourceTable.structure(), targetTable.structure());
				
				dataModelMappings.put(
					sourceTable.structure().id(), 
					targetTable.structure()
				);
				break;
			default:
				throw new IllegalArgumentException(String.format("EditableDataFieldWriter : %s style not supported !", source.style().toString())); 
		}
		
		return copy;
	}
	
}
