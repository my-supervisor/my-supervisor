package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="indicator_static_param",
		label="Paramètre statique d'indicateur",
		inheritFields=false
)
public interface IndicatorStaticParam extends IndicatorTypeStaticParam {

	@Field(
		label="Indicateur", 
		rel= Relation.MANY2ONE
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
