package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgType;
import com.minlessika.supervisor.domain.FormularCaseExpression;
import com.minlessika.supervisor.domain.WhenCase;
import com.minlessika.supervisor.domain.WhenCases;

public final class PxWhenCases extends DomainRecordables<WhenCase, WhenCases> implements WhenCases {

	private final FormularCaseExpression expression;
	
	public PxWhenCases(FormularCaseExpression expression) throws IOException {
		this(expression.listOf(WhenCase.class), expression);
	}
	
	public PxWhenCases(RecordSet<WhenCase> source, FormularCaseExpression expression) throws IOException {
		super(PxWhenCase.class, source);
		
		this.expression = expression;
		this.source = source.where(WhenCase::expression, this.expression.id())
							.orderBy(WhenCase::id);
	}

	@Override
	protected WhenCases domainSetOf(final RecordSet<WhenCase> source) throws IOException {
		return new PxWhenCases(source, expression);
	}
	
	private ExpressionArg createArg(int position) throws IOException {
		
		RecordSet<ExpressionArg> source = this.source.of(ExpressionArg.class);
		
		return new PxExpressionArg(
						source.entryOf(ExpressionArg::expression, expression.id())
					          .entryOf(ExpressionArg::type, ExpressionArgType.NONE)
					          .entryOf(ExpressionArg::no, position)
					          .add()
			   );
	}

	@Override
	public WhenCase add() throws IOException {
		
		// 1 - créer le paramètre de gauche
		final ExpressionArg leftArg = createArg(1);
		leftArg.update("1", DataFieldType.NUMBER);
		
		// 2 - créer le paramètre de droite
		final ExpressionArg rightArg = createArg(2);
		rightArg.update("1", DataFieldType.NUMBER);
	
		// 3 - créer le paramètre résultat
		final ExpressionArg result = createArg(3);
		result.update("1", DataFieldType.NUMBER);
		
		Record<WhenCase> record = source.entryOf(WhenCase::expression, expression.id())
										.entryOf(WhenCase::leftArg, leftArg.id())
				                        .entryOf(WhenCase::comparator, Comparator.EQUALS)
				                        .entryOf(WhenCase::rightArg, rightArg.id())
				                        .entryOf(WhenCase::result, result.id())
				                        .add();
				                        
		return new PxWhenCase(record);
	}

	@Override
	public void remove(WhenCase item) throws IOException{
		
		Long leftArgId = item.leftArg().id();
		Long rightArgId = item.rightArg().id();
		Long resultId = item.result().id();
		super.remove(item);
		
		RecordSet<ExpressionArg> argSource = source.of(ExpressionArg.class);
		argSource.remove(leftArgId);
		argSource.remove(rightArgId); 
		argSource.remove(resultId);
	}
}
