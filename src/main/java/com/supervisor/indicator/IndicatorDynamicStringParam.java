package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="supervisor_indicator_dynamic_string_param", 
		label="Paramètre dynamique d'indicateur de type nombre",
		comodel=IndicatorDynamicParam.class
)
public interface IndicatorDynamicStringParam extends IndicatorDynamicParam {

	@Field(label="Mise en forme", isMandatory=false)
	String markup() throws IOException;
	
	void update(String markup) throws IOException;
}
