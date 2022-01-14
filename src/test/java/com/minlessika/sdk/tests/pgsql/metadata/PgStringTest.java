package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgStringField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgStringTest {
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgStringField(1, "person", "name", "Nom complet").type().name(), 
				Matchers.equalTo("character varying")
		);
	}
}
