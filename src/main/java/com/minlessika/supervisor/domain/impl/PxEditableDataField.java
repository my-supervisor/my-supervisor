package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldDependencies;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;

public abstract class PxEditableDataField extends PxDataField implements EditableDataField {

	protected final Record<EditableDataField> editableRecord;
	
	public PxEditableDataField(Record<DataField> record) throws IOException {
		super(record);
		
		this.editableRecord = record.listOf(EditableDataField.class).get(record.id()); 
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {
		
		if(!code.equals("PARENT") && userScope() == UserScope.SYSTEM)
			throw new IllegalArgumentException("Vous ne pouvez pas modifier un champ système !");
		
		super.update(code, name, type, style, description);
	}

	@Override
	public int order() throws IOException {
		return editableRecord.valueOf(EditableDataField::order);
	}

	@Override
	public boolean isMandatory() throws IOException {
		return editableRecord.valueOf(EditableDataField::isMandatory);
	}

	@Override
	public UserScope userScope() throws IOException {
		return editableRecord.valueOf(EditableDataField::userScope);
	}

	@Override
	public void order(int order) throws IOException {
		
		editableRecord.mustCheckThisCondition(
				order >= 0, 
				"Le numéro d'ordre d'un champ de données doit être positif !"
		);
		
		editableRecord.mustCheckThisCondition(
				order%10 == 1 || order%10 == 2 || order%10 == 3 || order%10 == 4, 
				"Ordre : vous devez fournir un nombre entier ayant pour unité 1, 2, 3 ou 4 !"
		);
		
		editableRecord.entryOf(EditableDataField::order, order)
	      	  .update();
	}

	@Override
	public void makeMandatory(boolean mandatory) throws IOException {
		editableRecord.entryOf(EditableDataField::isMandatory, mandatory)
	          .update();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	@Override
	public DataSheetModel model() throws IOException {
		return new PxDataSheetModel(super.model());
	}

	@Override
	public void changeUserScope(UserScope scope) throws IOException {
		
		if(scope == UserScope.NONE)
			throw new IllegalArgumentException("Vous devez définir la portée du champ !");
		
		editableRecord.entryOf(EditableDataField::userScope, scope)
	          .update();
	}

	@Override
	public DataFieldDependencies dependencies() throws IOException {
		return new PgEditableDataFieldDependencies(this);
	}
}
