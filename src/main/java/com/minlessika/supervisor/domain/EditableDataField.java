package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_editable_data_field", 
	label="Champ de données éditable",
	comodel=DataField.class
)
public interface EditableDataField extends DataField {
	
	@Field(
		label="Modèle de feuilles de données", 
		rel=Relation.MANY2ONE,
		ignore=true
	)
	DataSheetModel model() throws IOException;
	
	@Field(name="no", label="Ordre")
	int order() throws IOException;
	
	@Field(label="Est obligatoire ?")
	boolean isMandatory() throws IOException;
	
	@Field(label="Portée du champ")
	UserScope userScope() throws IOException;
	
	void order(int order) throws IOException;
	void makeMandatory(boolean mandatory) throws IOException;
	void changeUserScope(UserScope scope) throws IOException;
}
