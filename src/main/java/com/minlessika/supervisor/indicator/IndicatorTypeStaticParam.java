package com.minlessika.supervisor.indicator;

import java.io.IOException;
import java.util.List;

import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable(
	comodel=IndicatorTypeParam.class
)
public interface IndicatorTypeStaticParam extends IndicatorTypeParam {

	@Field(label="Valeurs prédéfinies")
	List<String> predefinedValues() throws IOException;

	void predefine(List<String> values) throws IOException;
}
