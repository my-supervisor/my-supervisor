package com.minlessika.core.takes;

import com.minlessika.core.takes.BddVersion;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.utils.Version;
import com.minlessika.sdk.utils.VersionImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class PgBddVersion extends DomainRecordable<BddVersion> implements BddVersion {

	private final Version origin;
	
	public PgBddVersion(final Base base, final String product) throws IOException {
		super(
			base.select(BddVersion.class)
			    .where(BddVersion::tag, String.format("%s_VERSION", product.toUpperCase()))
			    .first()
		);
		
		origin = new VersionImpl(revision());
	}

	@Override
	public String releaseDate() throws IOException {
		return record.valueOf(BddVersion::releaseDate);
	}

	@Override
	public String revision() throws IOException {
		return record.valueOf(BddVersion::revision); 
	}

	@Override
	public String productRange() throws IOException {
		return record.valueOf(BddVersion::productRange);
	}

	@Override
	public String notes() throws IOException {
		return record.valueOf(BddVersion::notes);
	}

	@Override
	public void update(String revision, LocalDate releaseDate, String notes) throws IOException {
		
		record.entryOf(BddVersion::revision, revision)
		      .entryOf(BddVersion::releaseDate, releaseDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
		      .entryOf(BddVersion::notes, notes)
		      .update();
		
	}

	@Override
	public String value() throws IOException {
		return origin.value();
	}

	@Override
	public boolean isOldThan(String version) throws IOException {
		return origin.isOldThan(version);
	}

	@Override
	public boolean isNewThan(String version) throws IOException {
		return origin.isNewThan(version);
	}

	@Override
	public boolean isSameThan(String version) throws IOException {
		return origin.isSameThan(version);
	}
}
