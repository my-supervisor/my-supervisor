package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgLongField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgLongFieldTest {
	
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgLongField(1, "person", "id", "ID").type().name(), 
				Matchers.equalTo("bigint")
		);
	}
}
