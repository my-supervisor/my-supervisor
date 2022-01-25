package com.minlessika.sdk.tests.pgsql.metadata;

import com.supervisor.sdk.pgsql.metadata.PgDoubleField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgDoubleFieldTest {
	
	@Test
	public void typeNameTest() throws IOException {
		MatcherAssert.assertThat(
				new PgDoubleField(1, "account", "amount", "Montant").type().name(), 
				Matchers.equalTo("double precision")
		);
	}
}
