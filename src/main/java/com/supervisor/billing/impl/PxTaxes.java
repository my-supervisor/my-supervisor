package com.supervisor.billing.impl;

import com.supervisor.billing.Tax;
import com.supervisor.billing.Taxes;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;

public final class PxTaxes extends DomainRecordables<Tax, Taxes> implements Taxes {

	public PxTaxes(RecordSet<Tax> source) {
		super(PxTax.class, source);
	}

}
