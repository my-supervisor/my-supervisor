package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Language;
import com.minlessika.membership.domain.Languages;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;

public final class PxLanguages extends DomainRecordables<Language, Languages> implements Languages {

	public PxLanguages(RecordSet<Language> source) {
		super(PxLanguage.class, source);
	}
}
