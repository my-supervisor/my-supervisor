package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Tax;
import com.minlessika.membership.billing.Taxes;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;

public final class PxTaxes extends DomainRecordables<Tax, Taxes> implements Taxes {

	public PxTaxes(RecordSet<Tax> source) {
		super(PxTax.class, source);
	}

}
