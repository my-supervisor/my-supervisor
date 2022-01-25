package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
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
