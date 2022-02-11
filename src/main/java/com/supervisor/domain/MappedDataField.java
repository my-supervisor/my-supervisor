package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.indicator.IndicatorDynamicParam;

@com.supervisor.sdk.metadata.Recordable(
	name="mapped_data_field",
	label="Mapping d'une liaison de données"
)
public interface MappedDataField extends com.supervisor.sdk.datasource.Recordable {
	
	@Field(
		label="Paramètre de l'indicateur", 
		rel= Relation.MANY2ONE
	)
	IndicatorDynamicParam param() throws IOException;
	
	@Field(
		label="Liaison de données",
		rel=Relation.MANY2ONE
	)
	DataLink link() throws IOException;
	
	@Field(
		label="Operator"
	)
	DataLinkOperator operator() throws IOException;
	
	@Field(
		label="Champ à utiliser", 
		rel=Relation.MANY2ONE
	)
	DataField fieldToUse() throws IOException;	
	
	void update(DataLinkOperator operator, DataField field) throws IOException;
}
