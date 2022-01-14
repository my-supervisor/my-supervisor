package com.minlessika.core.takes;

import com.minlessika.core.takes.BddVersion;
import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface BddVersions extends DomainSet<BddVersion, com.minlessika.core.takes.BddVersions> {
	boolean contains(String module) throws IOException;
}
