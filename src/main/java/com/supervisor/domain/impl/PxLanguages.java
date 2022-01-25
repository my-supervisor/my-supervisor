package com.supervisor.domain.impl;

import com.supervisor.domain.Language;
import com.supervisor.domain.Languages;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;

public final class PxLanguages extends DomainRecordables<Language, Languages> implements Languages {

	public PxLanguages(RecordSet<Language> source) {
		super(PxLanguage.class, source);
	}
}
