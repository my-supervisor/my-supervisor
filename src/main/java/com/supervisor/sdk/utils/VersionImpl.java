package com.supervisor.sdk.utils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public final class VersionImpl implements Version {

	private final String value;
	
	public VersionImpl(final String value) {
		this.value = value;
	}
	
	@Override
	public String value() throws IOException {
		return value;
	}

	@Override
	public boolean isNewThan(String version) throws IOException {
		return numberOf(value) > numberOf(version); 
	}

	@Override
	public boolean isOldThan(String version) throws IOException {
		return numberOf(value) < numberOf(version);
	}

	@Override
	public boolean isSameThan(String version) throws IOException {
		return numberOf(value) == numberOf(version);
	}

	private static int numberOf(String revision) {
		String[] revisionParts = StringUtils.split(revision, '.');
		return Integer.parseInt(StringUtils.join(revisionParts));
	}
}
