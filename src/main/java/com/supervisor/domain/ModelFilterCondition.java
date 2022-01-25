package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
	name="supervisor_model_filter_condition", 
	label="Condition d'un filtre de modèle"
)
public interface ModelFilterCondition extends com.supervisor.sdk.datasource.Recordable {
	
	@Field(
		label="Filtre", 
		rel= Relation.MANY2ONE
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
