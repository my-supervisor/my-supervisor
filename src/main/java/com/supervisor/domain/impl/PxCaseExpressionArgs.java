package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionArgType;
import com.supervisor.domain.ExpressionArgs;
import com.supervisor.domain.FormularCaseExpression;

public final class PxCaseExpressionArgs extends DomainRecordables<ExpressionArg, ExpressionArgs> implements ExpressionArgs {

	private final FormularCaseExpression expression;
	
	public PxCaseExpressionArgs(final FormularCaseExpression expression) throws IOException {
		this(expression, expression.listOf(ExpressionArg.class));		
	}
	
	public PxCaseExpressionArgs(final FormularCaseExpression expression, final RecordSet<ExpressionArg> source) throws IOException {
		super(PxExpressionArg.class, source);
	
		this.expression = expression;
		this.source = source.where(ExpressionArg::expression, this.expression.id())
							.orderBy(ExpressionArg::id);
	}
	
	@Override
	protected ExpressionArgs domainSetOf(final RecordSet<ExpressionArg> source) throws IOException {
		return new PxCaseExpressionArgs(expression, source);
	}
	
	@Override
	protected ExpressionArg domainOf(final Record<ExpressionArg> record) throws IOException {
		return new ExpressionArgTypingImpl(record).argument();
	}

	@Override
	public ExpressionArg add(int no) throws IOException {
	
		final ExpressionArg item = new PxExpressionArg(
										source.entryOf(ExpressionArg::expression, expression.id())
										      .entryOf(ExpressionArg::type, ExpressionArgType.NONE)
										      .entryOf(ExpressionArg::no, no)
										      .add()
								   );
		
		item.update("1", DataFieldType.NUMBER);
		return new PxValueArg(item);
	}

	@Override
	public ExpressionArg addDataFieldArg(int no) throws IOException {
		final ExpressionArg item = new PxExpressionArg(
				source.entryOf(ExpressionArg::expression, expression.id())
				      .entryOf(ExpressionArg::type, ExpressionArgType.NONE)
				      .entryOf(ExpressionArg::no, no)
				      .add()
		   );

		item.update((EditableDataField)expression.formular().model().model().fields().get("REF"));
		return new PxEditableDataFieldArg(item);
	}
}
