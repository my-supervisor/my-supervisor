package com.minlessika.sdk.tests.pgsql.metadata;

import com.supervisor.sdk.pgsql.metadata.PgIntegerField;
import com.supervisor.sdk.pgsql.metadata.PgPrimaryKeyField;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public final class PgPrimaryKeyFieldTest {
	
	@Test
	public void defintionScriptTest() throws IOException {
		MatcherAssert.assertThat(
				new PgPrimaryKeyField(
					new PgIntegerField(1, "person", "id", "ID")
				).definitionScript(), 
				Matchers.equalTo("id integer NOT NULL")
		);
	}
	
	@Test
	public void constraintScriptTest() throws IOException {
		MatcherAssert.assertThat(
				new PgPrimaryKeyField(
					new PgIntegerField(1, "person", "id", "ID")
				).constraintScript(), 
				Matchers.equalTo("CONSTRAINT person_pkey PRIMARY KEY (id)")
		);
	}
}
