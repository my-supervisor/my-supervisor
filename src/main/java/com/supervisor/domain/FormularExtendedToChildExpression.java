package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	comodel=FormularExpression.class
)
public interface FormularExtendedToChildExpression extends FormularExpression {
	
	@Field(label="Aggregate", name="extended_child_aggregate")
	AggregateFunc aggregate() throws IOException;	
	
	EditableDataField child() throws IOException;
	
	void update(EditableDataField child, AggregateFunc aggregate) throws IOException;
}
