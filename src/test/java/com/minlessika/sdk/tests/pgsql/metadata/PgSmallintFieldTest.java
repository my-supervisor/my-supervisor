package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgSmallintField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgSmallintFieldTest {
	
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgSmallintField(1, "person", "id", "ID").type().name(), 
				Matchers.equalTo("smallint")
		);
	}
}
