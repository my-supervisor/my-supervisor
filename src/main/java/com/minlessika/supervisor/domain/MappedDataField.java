package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_mapped_data_field", 
	label="Mapping d'une liaison de données"
)
public interface MappedDataField extends Recordable {
	
	@Field(
		label="Paramètre de l'indicateur", 
		rel=Relation.MANY2ONE
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
