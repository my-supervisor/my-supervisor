package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.WhenCase;

public final class PxWhenCase extends DomainRecordable<WhenCase> implements WhenCase {

	public PxWhenCase(Record<WhenCase> record) throws IOException {
		super(record);
	}

	@Override
	public FormularCaseExpression expression() throws IOException {
		Record<FormularCaseExpression> rec = record.of(WhenCase::expression);
		return new PxFormularCaseExpression(rec.listOf(FormularExpression.class).get(rec.id())); 
	}

	@Override
	public ExpressionArg leftArg() throws IOException {
		Record<ExpressionArg> rec = record.of(WhenCase::leftArg);
		return new ExpressionArgTypingImpl(rec).argument();
	}

	@Override
	public Comparator comparator() throws IOException {
		return record.valueOf(WhenCase::comparator);
	}

	@Override
	public ExpressionArg rightArg() throws IOException {
		Record<ExpressionArg> rec = record.of(WhenCase::rightArg);
		return new ExpressionArgTypingImpl(rec).argument();
	}

	@Override
	public ExpressionArg result() throws IOException {
		Record<ExpressionArg> rec = record.of(WhenCase::result);
		return new ExpressionArgTypingImpl(rec).argument();
	}

	@Override
	public void updateComparator(Comparator comparator) throws IOException {
		
		record.mustCheckThisCondition(
				comparator != Comparator.NONE, 
				"Vous devez spécifier un opérateur de comparaison !"
		);
		
		record.entryOf(WhenCase::comparator, comparator)
		      .update();
	}
}
