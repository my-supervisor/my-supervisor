package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgType;
import com.minlessika.supervisor.domain.ExpressionArgs;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;

public final class PxExtendedToModelExpressionArgs extends DomainRecordables<ExpressionArg, ExpressionArgs> implements ExpressionArgs {

	private final FormularExtendedToModelExpression expression;
	
	public PxExtendedToModelExpressionArgs(final FormularExtendedToModelExpression expression) throws IOException {
		this(expression, expression.listOf(ExpressionArg.class));		
	}
	
	public PxExtendedToModelExpressionArgs(final FormularExtendedToModelExpression expression, final RecordSet<ExpressionArg> source) throws IOException {
		super(PxExpressionArg.class, source);
	
		this.expression = expression;
		this.source = source.where(ExpressionArg::expression, this.expression.id())
				            .orderBy(ExpressionArg::id);
	}
	
	@Override
	protected ExpressionArgs domainSetOf(final RecordSet<ExpressionArg> source) throws IOException {
		return new PxExtendedToModelExpressionArgs(expression, source);
	}
	
	@Override
	protected ExpressionArg domainOf(final Record<ExpressionArg> record) throws IOException {
		return new ExpressionArgTypingImpl(record).argument();
	}
	
	@Override
	public ExpressionArg add(int no) throws IOException {
		
		if(no != 0)
			throw new IllegalArgumentException("L'expression Etendue au model n'accepte qu'un argument de position 0 !"); 
		
		if(where(ExpressionArg::no, no).any())
			return where(ExpressionArg::no, no).first();
		
		final ExpressionArg item = new PxExpressionArg(
										source.entryOf(ExpressionArg::expression, expression.id())
										      .entryOf(ExpressionArg::type, ExpressionArgType.NONE)
										      .entryOf(ExpressionArg::no, no)
										      .add()
								   );

		item.update("0", DataFieldType.NUMBER);
		
		return new PxValueArg(item);
	}
	
	@Override
	public ExpressionArg addDataFieldArg(int no) throws IOException {
		throw new UnsupportedOperationException("PxExtendedToModelExpressionArgs#addDataFieldArg");
	}
}
