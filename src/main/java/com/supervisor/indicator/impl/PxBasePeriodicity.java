package com.supervisor.indicator.impl;

import java.io.IOException;
import java.time.LocalDate;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.time.Periodicity;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.sdk.time.SimplePeriodicity;
import com.supervisor.indicator.BasePeriodicity;

public final class PxBasePeriodicity extends DomainRecordable<BasePeriodicity> implements BasePeriodicity {
	
	Periodicity origin;
	
	public PxBasePeriodicity(final Record<BasePeriodicity> source) throws IOException {
		super(source);
		
		origin = new SimplePeriodicity(
					source.valueOf(BasePeriodicity::number), 
					source.valueOf(BasePeriodicity::unit), 
					source.valueOf(BasePeriodicity::reference),
					source.valueOf(BasePeriodicity::closeInterval)
				);
	}

	@Override
	public int number() {
		return origin.number();
	}

	@Override
	public PeriodicityUnit unit() {
		return origin.unit();
	}

	@Override
	public LocalDate reference() {
		return origin.reference();
	}

	@Override
	public LocalDate begin(LocalDate date) {
		return origin.begin(date);
	}

	@Override
	public LocalDate end(LocalDate date) {
		return origin.end(date);
	}

	@Override
	public boolean closeInterval() {
		return origin.closeInterval();
	}	
}
