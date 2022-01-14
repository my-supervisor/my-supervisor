package com.minlessika.supervisor.domain;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	comodel=FormularExpression.class
)
public interface FormularExtendedToParentExpression extends FormularExpression {
	ListDataField reference() throws IOException;
	FormularExtendedToParentSources sources() throws IOException;
	boolean isBasedOn(DataModel model) throws IOException;
}
