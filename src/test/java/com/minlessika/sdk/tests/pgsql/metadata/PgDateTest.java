package com.minlessika.sdk.tests.pgsql.metadata;

import com.supervisor.sdk.pgsql.metadata.PgDateField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgDateTest {
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgDateField(1, "person", "birth_date", "Date de naissance").type().name(), 
				Matchers.equalTo("date")
		);
	}
}
