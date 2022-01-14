package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.supervisor.domain.bi.AggregateFunc;

@com.minlessika.sdk.metadata.Recordable(
	comodel=FormularExpression.class
)
public interface FormularExtendedToChildExpression extends FormularExpression {
	
	@Field(label="Aggregate", name="extended_child_aggregate")
	AggregateFunc aggregate() throws IOException;	
	
	EditableDataField child() throws IOException;
	
	void update(EditableDataField child, AggregateFunc aggregate) throws IOException;
}
