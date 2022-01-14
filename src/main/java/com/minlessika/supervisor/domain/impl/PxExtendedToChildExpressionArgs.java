package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgType;
import com.minlessika.supervisor.domain.ExpressionArgs;
import com.minlessika.supervisor.domain.FormularExtendedToChildExpression;

public final class PxExtendedToChildExpressionArgs extends DomainRecordables<ExpressionArg, ExpressionArgs> implements ExpressionArgs {

	private final FormularExtendedToChildExpression expression;
	
	public PxExtendedToChildExpressionArgs(final FormularExtendedToChildExpression expression) throws IOException {
		this(expression, expression.listOf(ExpressionArg.class));		
	}
	
	public PxExtendedToChildExpressionArgs(final FormularExtendedToChildExpression expression, final RecordSet<ExpressionArg> source) throws IOException {
		super(PxExpressionArg.class, source);
	
		this.expression = expression;
		this.source = source.where(ExpressionArg::expression, this.expression.id())
				            .orderBy(ExpressionArg::id);
	}
	
	@Override
	protected ExpressionArgs domainSetOf(final RecordSet<ExpressionArg> source) throws IOException {
		return new PxExtendedToChildExpressionArgs(expression, source);
	}
	
	@Override
	protected ExpressionArg domainOf(final Record<ExpressionArg> record) throws IOException {
		return new ExpressionArgTypingImpl(record).argument();
	}
	
	@Override
	public ExpressionArg add(int no) throws IOException {
		return addDataFieldArg(no);
	}
	
	@Override
	public ExpressionArg addDataFieldArg(int no) throws IOException {
		
		if(no != 1)
			throw new IllegalArgumentException("L'expression Etendue à l'enfant n'accepte qu'un seul argument de position 1 !"); 
		
		if(where(ExpressionArg::no, no).any())
			return where(ExpressionArg::no, no).first();
		
		final ExpressionArg item = new PxExpressionArg(
										source.entryOf(ExpressionArg::expression, expression.id())
										      .entryOf(ExpressionArg::type, ExpressionArgType.NONE)
										      .entryOf(ExpressionArg::no, no)
										      .add()
								   );

		final DataSheetModel model = expression.formular().model().coreModel();
		item.update((EditableDataField)model.fields().get("REF")); // for creation. It will be updated later
		
		return new PxEditableDataFieldArg(item);
	}
}
