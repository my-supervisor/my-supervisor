package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_indicator_static_param", 
		label="Paramètre statique d'indicateur",
		inheritFields=false
)
public interface IndicatorStaticParam extends IndicatorTypeStaticParam {

	@Field(
		label="Indicateur", 
		rel=Relation.MANY2ONE
	)
	Indicator indicator() throws IOException;
	
	@Field(
		label="Origine", 
		rel=Relation.MANY2ONE
	)
	IndicatorTypeStaticParam origin() throws IOException;
	
	@Field(label="Valeur")
	String value() throws IOException;
	
	void update(String value) throws IOException;
}
