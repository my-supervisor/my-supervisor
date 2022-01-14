package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_model_filter_condition", 
	label="Condition d'un filtre de modèle"
)
public interface ModelFilterCondition extends Recordable {
	
	@Field(
		label="Filtre", 
		rel=Relation.MANY2ONE
	)
	ModelFilter filter() throws IOException;
	
	@Field(
		label="Champ de données", 
		rel=Relation.MANY2ONE
	)
	DataField field() throws IOException;
	
	@Field(label="Comparateur")
	Comparator comparator() throws IOException;
	
	@Field(label="Valeur", isMandatory=false)
	String value() throws IOException;
	
	void update(DataField field, Comparator comparator, String value) throws IOException;
}
