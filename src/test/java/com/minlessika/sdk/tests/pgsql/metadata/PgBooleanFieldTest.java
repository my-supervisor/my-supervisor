package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgBooleanField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgBooleanFieldTest {
	
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgBooleanField(1, "person", "is_male", "Est un homme").type().name(), 
				Matchers.equalTo("boolean")
		);
	}
}
