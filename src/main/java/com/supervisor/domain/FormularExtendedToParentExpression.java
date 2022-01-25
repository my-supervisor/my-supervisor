package com.supervisor.domain;

import com.supervisor.sdk.metadata.Recordable;

import java.io.IOException;

@Recordable(
	comodel=FormularExpression.class
)
public interface FormularExtendedToParentExpression extends FormularExpression {
	ListDataField reference() throws IOException;
	FormularExtendedToParentSources sources() throws IOException;
	boolean isBasedOn(DataModel model) throws IOException;
}
