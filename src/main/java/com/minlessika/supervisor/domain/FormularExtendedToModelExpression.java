package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.supervisor.domain.bi.AggregateFunc;

@com.minlessika.sdk.metadata.Recordable(
	comodel=FormularExpression.class
)
public interface FormularExtendedToModelExpression extends FormularExpression {
	
	@Field(label="Aggregate", name="extended_model_aggregate")
	AggregateFunc aggregate() throws IOException;
	
	ExpressionArg defaultValue() throws IOException;
	
	FormularExtendedToModelSources sources() throws IOException;
	boolean isBasedOn(DataModel model) throws IOException;
	boolean isStrictBasedOn(DataModel model) throws IOException;
	
	void update(AggregateFunc aggregate) throws IOException;
}
