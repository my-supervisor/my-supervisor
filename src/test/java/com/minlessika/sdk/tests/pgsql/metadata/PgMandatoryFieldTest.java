package com.minlessika.sdk.tests.pgsql.metadata;

import com.minlessika.sdk.pgsql.metadata.PgMandatoryField;
import com.minlessika.sdk.pgsql.metadata.PgStringField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgMandatoryFieldTest {
	
	@Test
	public void defintionScriptTest() throws IOException {
		MatcherAssert.assertThat(
				new PgMandatoryField(
					new PgStringField(1, "person", "name", "Nom complet")
				).definitionScript(), 
				Matchers.equalTo("name character varying NOT NULL")
		);
	}
}
