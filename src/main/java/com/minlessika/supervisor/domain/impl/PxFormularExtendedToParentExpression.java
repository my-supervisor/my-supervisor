package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.EditableDataFieldArg;
import com.minlessika.supervisor.domain.ExpressionArgs;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSources;
import com.minlessika.supervisor.domain.ListDataField;

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
