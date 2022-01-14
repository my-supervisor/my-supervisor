package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgDateTimeField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgDateTimeTest {
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgDateTimeField(1, "person", "creation_date", "Date de création").type().name(), 
				Matchers.equalTo("timestamp without time zone")
		);
	}
}
