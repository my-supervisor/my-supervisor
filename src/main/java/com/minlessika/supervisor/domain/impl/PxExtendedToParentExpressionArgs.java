package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgType;
import com.minlessika.supervisor.domain.ExpressionArgs;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;

public final class PxExtendedToParentExpressionArgs extends DomainRecordables<ExpressionArg, ExpressionArgs> implements ExpressionArgs {

	private final FormularExtendedToParentExpression expression;
	
	public PxExtendedToParentExpressionArgs(final FormularExtendedToParentExpression expression) throws IOException {
		this(expression, expression.listOf(ExpressionArg.class));		
	}
	
	public PxExtendedToParentExpressionArgs(final FormularExtendedToParentExpression expression, final RecordSet<ExpressionArg> source) throws IOException {
		super(PxExpressionArg.class, source);
	
		this.expression = expression;
		this.source = source.where(ExpressionArg::expression, this.expression.id())
				            .orderBy(ExpressionArg::id);
	}
	
	@Override
	protected ExpressionArgs domainSetOf(final RecordSet<ExpressionArg> source) throws IOException {
		return new PxExtendedToParentExpressionArgs(expression, source);
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
			throw new IllegalArgumentException("L'expression Etendue au parent n'accepte qu'un argument de position 1 !"); 
		
		if(where(ExpressionArg::no, no).any())
			return where(ExpressionArg::no, no).first();
		
		final ExpressionArg item = new PxExpressionArg(
										source.entryOf(ExpressionArg::expression, expression.id())
										      .entryOf(ExpressionArg::type, ExpressionArgType.NONE)
										      .entryOf(ExpressionArg::no, no)
										      .add()
								   );

		final DataSheetModel model = expression.formular().model().coreModel();
		final List<DataSheetModel> parents = model.parents().items();
		if(parents.isEmpty())
			throw new IllegalArgumentException(String.format("%s : le modèle ne possède pas de parent !", model.name()));
		
		item.update((EditableDataField)parents.get(0).fields().get("REF"));
		
		return new PxEditableDataFieldArg(item);
	}
}
