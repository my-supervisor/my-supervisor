package com.supervisor.domain.impl;

import java.io.IOException;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionArgs;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSources;
import com.supervisor.domain.bi.AggregateFunc;

public final class PxFormularExtendedToModelExpression extends PxBasicFormularExpression implements FormularExtendedToModelExpression {

	private final Record<FormularExtendedToModelExpression> record;
	
	public PxFormularExtendedToModelExpression(Record<FormularExpression> record) throws IOException {
		super(record);
		
		this.record = record.listOf(FormularExtendedToModelExpression.class).get(record.id());
	}

	@Override
	public String toText() throws IOException {
		return String.format("ExtensionOf::%s", formular().model().name());
	}

	@Override
	public ExpressionArgs arguments() throws IOException {
		return new PxExtendedToModelExpressionArgs(this);
	}

	@Override
	public ExpressionArg defaultValue() throws IOException {
		return arguments().where(ExpressionArg::no, 0).first();
	}

	@Override
	public FormularExtendedToModelSources sources() throws IOException {
		return new PgFormularExtendedToModelSources(this);
	}

	@Override
	public boolean isBasedOn(DataModel model) throws IOException {
		return sources().isBasedOn(model);
	}
	
	@Override
	public boolean isStrictBasedOn(DataModel model) throws IOException {
		return sources().isStrictBasedOn(model);
	}

	@Override
	public AggregateFunc aggregate() throws IOException {
		return record.valueOf(FormularExtendedToModelExpression::aggregate);
	}

	@Override
	public void update(AggregateFunc aggregate) throws IOException {
		record.entryOf(FormularExtendedToModelExpression::aggregate, aggregate)
		      .update(); 
	}
}
