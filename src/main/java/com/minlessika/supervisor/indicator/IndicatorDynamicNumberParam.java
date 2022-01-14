package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_indicator_dynamic_number_param", 
		label="Paramètre dynamique d'indicateur de type nombre",
		comodel=IndicatorDynamicParam.class
)
public interface IndicatorDynamicNumberParam extends IndicatorDynamicParam {

	@Field(label="Précision")
	int precision() throws IOException;
	
	@Field(label="Appliquer séparateur de milliers ?")
	boolean applyThousandsSeparator() throws IOException;
	
	void update(int precision, boolean applyThousandsSeparator) throws IOException;
}
