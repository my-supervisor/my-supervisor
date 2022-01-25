package com.supervisor.domain.impl;

import com.supervisor.domain.Sequence;
import com.supervisor.domain.Sequences;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxSequences extends DomainRecordables<Sequence, Sequences> implements Sequences {

	public PxSequences(RecordSet<Sequence> source) {
		super(PxSequence.class, source);
	}

	@Override
	public Sequence add(String code, String name, String prefix, String suffix, int size, int step, long nextNumber)
			throws IOException {
		
		source.isRequired(Sequence::name, name);
		source.mustBeUnique(Sequence::code, code);		
		source.mustCheckThisCondition(step > 0, "Le pas doit être supérieur à 0 !");
		source.mustCheckThisCondition(nextNumber > 0, "Le prochain nombre doit être supérieur à 0 !");
		source.mustCheckThisCondition(size >= 0, "Le taille doit être positive !");
		
		Record<Sequence> record = source.entryOf(Sequence::code, code)
									    .entryOf(Sequence::name, name)
								        .entryOf(Sequence::prefix, prefix)
								        .entryOf(Sequence::suffix, suffix)
								        .entryOf(Sequence::size, size)
								        .entryOf(Sequence::step, step)
								        .entryOf(Sequence::nextNumber, nextNumber)
								        .add();
		
		return domainOf(record);
	}

	@Override
	public Sequence get(String code) throws IOException {
		return where(Sequence::code, code).first();
	}
}
