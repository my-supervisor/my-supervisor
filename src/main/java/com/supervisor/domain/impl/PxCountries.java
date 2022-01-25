package com.supervisor.domain.impl;

import com.supervisor.domain.Countries;
import com.supervisor.domain.Country;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;

public final class PxCountries extends DomainRecordables<Country, Countries> implements Countries {

	public PxCountries(RecordSet<Country> source) {
		super(PxCountry.class, source);
	}

}
