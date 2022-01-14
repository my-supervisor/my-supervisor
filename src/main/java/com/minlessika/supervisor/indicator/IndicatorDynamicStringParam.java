package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_indicator_dynamic_string_param", 
		label="Paramètre dynamique d'indicateur de type nombre",
		comodel=IndicatorDynamicParam.class
)
public interface IndicatorDynamicStringParam extends IndicatorDynamicParam {

	@Field(label="Mise en forme", isMandatory=false)
	String markup() throws IOException;
	
	void update(String markup) throws IOException;
}
