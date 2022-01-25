package com.supervisor.domain.impl;

import com.supervisor.domain.Sequence;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.time.LocalDate;

public final class PxSequence extends DomainRecordable<Sequence> implements Sequence {
	
	private final LocalDate dateReference;
	private static final String YEAR_FORMAT = "{year}";
	private static final String MONTH_FORMAT = "{month}";
	private static final String DAY_FORMAT = "{day}";
	
	public PxSequence(Record<Sequence> record) throws IOException {
		this(record, LocalDate.now());
	}
	
	public PxSequence(Record<Sequence> record, LocalDate dateReference) throws IOException {
		super(record);
		
		this.dateReference = dateReference;
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(Sequence::code);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Sequence::name);
	}

	@Override
	public String prefix() throws IOException {
		return record.valueOf(Sequence::prefix);
	}

	@Override
	public String suffix() throws IOException {
		return record.valueOf(Sequence::suffix);
	}

	@Override
	public int size() throws IOException {
		return record.valueOf(Sequence::size);
	}

	@Override
	public int step() throws IOException {
		return record.valueOf(Sequence::step);
	}

	@Override
	public long nextNumber() throws IOException {
		return record.valueOf(Sequence::nextNumber);
	}

	private boolean dateReferenceChanged() throws IOException {
		
		String format = String.format("%s %s", prefix(), suffix());
		LocalDate lastModifiedDate = lastModificationDate().toLocalDate();
		boolean dateReferenceChanged = false;
		
		if(format.contains(YEAR_FORMAT)) {
			dateReferenceChanged = lastModifiedDate.getYear() != dateReference.getYear();
		}
		
		if(format.contains(MONTH_FORMAT) && !dateReferenceChanged) {
			dateReferenceChanged = lastModifiedDate.getMonthValue() != dateReference.getMonthValue();
		}
		
		if(format.contains(DAY_FORMAT) && !dateReferenceChanged) {
			dateReferenceChanged = lastModifiedDate.getDayOfMonth() != dateReference.getDayOfMonth();
		}
		
		return dateReferenceChanged;
	}
	
	@Override
	public synchronized String generate() throws IOException {
		
		String reference = StringUtils.EMPTY;
		
		String prefix = prefix();
		if(StringUtils.isNotBlank(prefix))
			reference += prefix;
		
		boolean dateReferenceChanged = dateReferenceChanged();
		
		final long nextNumber;
		if(dateReferenceChanged)
			nextNumber = 1; // réinitialiser le prochain nombre au changement de la date de référence
		else
			nextNumber = nextNumber();
		
		record.entryOf(Sequence::nextNumber, nextNumber + step())
		      .update();
		
		int size = size();
		reference += (size == 0) ? nextNumber : StringUtils.leftPad(Long.toString(nextNumber), size, "0");

		String suffix = suffix();
		if(StringUtils.isNotBlank(suffix))
			reference += suffix;
		
		// changer les mots-clés par les valeurs
		// 1 - année
		reference = reference.replace(YEAR_FORMAT, Integer.toString(dateReference.getYear()));
		
		// 2 - mois
		reference = reference.replace(MONTH_FORMAT, StringUtils.leftPad(Integer.toString(dateReference.getMonthValue()), 2, "0"));
		
		// 3 - jour
		reference = reference.replace(DAY_FORMAT, StringUtils.leftPad(Integer.toString(dateReference.getDayOfMonth()), 2, "0"));
		
		return reference;
	}

	@Override
	public void define(String name, String prefix, String suffix, int size, int step, long nextNumber)
			throws IOException {
		
		record.isRequired(Sequence::name, name);
		record.mustCheckThisCondition(step > 0, "Le pas doit être supérieur à 0 !");
		record.mustCheckThisCondition(nextNumber > 0, "Le prochain nombre doit être supérieur à 0 !");
		record.mustCheckThisCondition(size >= 0, "Le taille doit être positive !");
		
		record.entryOf(Sequence::name, name)
		      .entryOf(Sequence::prefix, prefix)
		      .entryOf(Sequence::suffix, suffix)
		      .entryOf(Sequence::size, size)
		      .entryOf(Sequence::step, step)
		      .entryOf(Sequence::nextNumber, nextNumber)
		      .update();
	}
	
	@Override
	public Sequence withReference(LocalDate date) throws IOException {
		return new PxSequence(record, date);
	}

}
