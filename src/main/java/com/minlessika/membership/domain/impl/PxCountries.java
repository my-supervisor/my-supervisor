package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Countries;
import com.minlessika.membership.domain.Country;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;

public final class PxCountries extends DomainRecordables<Country, Countries> implements Countries {

	public PxCountries(RecordSet<Country> source) {
		super(PxCountry.class, source);
	}

}
