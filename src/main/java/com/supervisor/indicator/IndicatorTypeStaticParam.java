package com.supervisor.indicator;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	comodel=IndicatorTypeParam.class
)
public interface IndicatorTypeStaticParam extends IndicatorTypeParam {

	@Field(label="Valeurs prédéfinies")
	List<String> predefinedValues() throws IOException;

	void predefine(List<String> values) throws IOException;
}
