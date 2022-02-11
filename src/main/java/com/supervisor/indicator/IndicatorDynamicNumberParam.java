package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="indicator_dynamic_number_param",
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
