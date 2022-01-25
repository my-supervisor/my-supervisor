package com.minlessika.sdk.tests.pgsql.metadata;

import com.supervisor.sdk.pgsql.metadata.PgIntegerField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgIntegerFieldTest {
	
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgIntegerField(1, "person", "id", "ID").type().name(), 
				Matchers.equalTo("integer")
		);
	}
}
