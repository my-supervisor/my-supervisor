package com.minlessika.sdk.tests.pgsql.metadata;

import com.supervisor.sdk.pgsql.metadata.PgUuidField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgUuidTest {
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgUuidField(1, "person", "guid", "GUID").type().name(), 
				Matchers.equalTo("uuid")
		);
	}
}
