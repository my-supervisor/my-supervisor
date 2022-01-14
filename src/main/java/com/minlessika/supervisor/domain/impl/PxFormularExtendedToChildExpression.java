package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.EditableDataFieldArg;
import com.minlessika.supervisor.domain.ExpressionArgs;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularExtendedToChildExpression;
import com.minlessika.supervisor.domain.bi.AggregateFunc;

public final class PxFormularExtendedToChildExpression extends PxBasicFormularExpression implements FormularExtendedToChildExpression {

	private final Record<FormularExtendedToChildExpression> record;
	
	public PxFormularExtendedToChildExpression(Record<FormularExpression> record) throws IOException {
		super(record);
		
		this.record = record.listOf(FormularExtendedToChildExpression.class).get(record.id());
	}

	@Override
	public String toText() throws IOException {
		final EditableDataField child = child();
		return String.format("%s (%s::%s)", aggregate().name(), child.model().name(), child.code());
	}

	@Override
	public ExpressionArgs arguments() throws IOException {
		return new PxExtendedToChildExpressionArgs(this);
	}

	@Override
	public EditableDataField child() throws IOException {
		final EditableDataFieldArg arg = (EditableDataFieldArg)arguments().items().get(0);
		return arg.field();
	}

	@Override
	public void update(EditableDataField child, AggregateFunc aggregate) throws IOException {
		
		record.mustCheckThisCondition(
				aggregate != AggregateFunc.NONE, 
				"Vous devez spécifier un agrégat !"
		);
		
		final EditableDataFieldArg arg = (EditableDataFieldArg)arguments().first();
		arg.update(child);
		
		record.entryOf(FormularExtendedToChildExpression::aggregate, aggregate)
	          .update();
	}

	@Override
	public AggregateFunc aggregate() throws IOException {
		return record.valueOf(FormularExtendedToChildExpression::aggregate);
	}
}
