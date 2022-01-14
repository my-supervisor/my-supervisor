package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Language;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;

public final class PxLanguage extends DomainRecordable<Language> implements Language {

	public PxLanguage(Record<Language> record) throws IOException {
		super(record);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Language::name);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(Language::code);
	}

	@Override
	public String isoCode() throws IOException {
		return record.valueOf(Language::isoCode);
	}

	@Override
	public boolean enabled() throws IOException {
		return record.valueOf(Language::enabled);
	}
}
