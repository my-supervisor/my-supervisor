package com.minlessika.core.takes;

import com.minlessika.core.takes.BddVersion;
import com.minlessika.core.takes.BddVersions;
import com.minlessika.core.takes.PgBddVersion;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PgBddVersions extends DomainRecordables<BddVersion, BddVersions> implements BddVersions {

	private final Base base;
	
	public PgBddVersions(final Base base) throws IOException {
		this(base.select(BddVersion.class), base);
	}
	
	private PgBddVersions(final RecordSet<BddVersion> source, final Base base) {
		super(PgBddVersion.class, source);
		
		this.base = base;
	}

	@Override
	protected com.minlessika.core.takes.PgBddVersions domainSetOf(final RecordSet<BddVersion> source) throws IOException {
		return new com.minlessika.core.takes.PgBddVersions(source, base);
	}
	
	@Override
	public boolean contains(String module) throws IOException {
		return where(BddVersion::tag, String.format("%s_VERSION", module.toUpperCase())).any();
	}

}
