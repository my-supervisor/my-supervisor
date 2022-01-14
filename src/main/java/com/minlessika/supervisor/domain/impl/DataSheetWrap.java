package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordableWrap;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataFieldOfSheets;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.IndicatorDependencies;

public class DataSheetWrap extends DomainRecordableWrap implements DataSheet {

	private final DataSheet origin;
	
	public DataSheetWrap(final DataSheet origin) {
		super(origin);
		
		this.origin = origin;
	}

	@Override
	public DataSheetModel model() throws IOException {
		return origin.model();
	}

	@Override
	public String reference() throws IOException {
		return origin.reference();
	}

	@Override
	public LocalDate date() throws IOException {
		return origin.date();
	}

	@Override
	public User owner() throws IOException {
		return origin.owner();
	}

	@Override
	public User creator() throws IOException {
		return origin.creator();
	}

	@Override
	public DataFieldOfSheets fields() throws IOException {
		return origin.fields();
	}

	@Override
	public void update(LocalDate date) throws IOException {
		origin.update(date);
	}

	@Override
	public void validate() throws IOException {
		origin.validate();
	}

	@Override
	public Activity activity() throws IOException {
		return origin.activity();
	}

	@Override
	public IndicatorDependencies indicatorsThatDependsOn() throws IOException {
		return origin.indicatorsThatDependsOn();
	}
}
