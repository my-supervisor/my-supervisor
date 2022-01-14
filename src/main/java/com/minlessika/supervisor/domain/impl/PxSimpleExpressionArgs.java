package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgType;
import com.minlessika.supervisor.domain.ExpressionArgs;
import com.minlessika.supervisor.domain.FormularSimpleExpression;

public final class PxSimpleExpressionArgs extends DomainRecordables<ExpressionArg, ExpressionArgs> implements ExpressionArgs {

	private final FormularSimpleExpression expression;
	
	public PxSimpleExpressionArgs(final FormularSimpleExpression expression) throws IOException {
		this(expression, expression.listOf(ExpressionArg.class));		
	}
	
	public PxSimpleExpressionArgs(final FormularSimpleExpression expression, final RecordSet<ExpressionArg> source) throws IOException {
		super(PxExpressionArg.class, source);
	
		this.expression = expression;
		this.source = source.where(ExpressionArg::expression, this.expression.id())
				            .orderBy(ExpressionArg::id);
	}
	
	@Override
	protected ExpressionArgs domainSetOf(final RecordSet<ExpressionArg> source) throws IOException {
		return new PxSimpleExpressionArgs(expression, source);
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

		item.update((EditableDataField)expression.formular().model().fields().get("REF"));
		return new PxEditableDataFieldArg(item);
	}
}
