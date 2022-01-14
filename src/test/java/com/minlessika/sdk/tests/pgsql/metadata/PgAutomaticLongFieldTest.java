package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgAutomaticLongField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgAutomaticLongFieldTest {
	
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgAutomaticLongField(1, "person", "id", "ID").type().name(), 
				Matchers.equalTo("bigserial")
		);
	}
}
