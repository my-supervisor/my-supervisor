package com.supervisor.domain.impl;

import java.io.IOException;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.EditableDataFieldArg;
import com.supervisor.domain.ExpressionArgs;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSources;
import com.supervisor.domain.ListDataField;

public final class PxFormularExtendedToParentExpression extends PxBasicFormularExpression implements FormularExtendedToParentExpression {

	public PxFormularExtendedToParentExpression(Record<FormularExpression> record) throws IOException {
		super(record);
	}

	@Override
	public String toText() throws IOException {
		return String.format("ExtensionOf::%s", reference().name());
	}

	@Override
	public ExpressionArgs arguments() throws IOException {
		return new PxExtendedToParentExpressionArgs(this);
	}

	@Override
	public ListDataField reference() throws IOException {
		final EditableDataFieldArg arg = (EditableDataFieldArg)arguments().items().get(0);
		return (ListDataField)arg.field();
	}

	@Override
	public FormularExtendedToParentSources sources() throws IOException {
		return new PgFormularExtendedToParentSources(this);
	}

	@Override
	public boolean isBasedOn(DataModel model) throws IOException {
		return sources().isBasedOn(model);
	}
}
