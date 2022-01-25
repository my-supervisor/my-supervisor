package com.minlessika.sdk.tests.pgsql.metadata;

import com.supervisor.sdk.pgsql.metadata.PgFieldMetadata;
import com.minlessika.sdk.tests.forks.FakeIntegerType;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public class BasicFieldTest {
	
	@Test
	public void commentScriptTest() throws IOException {
		MatcherAssert.assertThat(
				new PgFieldMetadata(1, "person", "id", new FakeIntegerType(), "ID").commentScript(), 
				Matchers.equalTo("COMMENT ON COLUMN person.id IS 'ID'")
		);
	}
	
	@Test
	public void definitionTest() throws IOException {
		MatcherAssert.assertThat(
				new PgFieldMetadata(1, "person", "id", new FakeIntegerType(), "ID").definitionScript(), 
				Matchers.equalTo("id integer")
		);
	}
	
	@Test
	public void constrainTest() throws IOException {
		MatcherAssert.assertThat(
				new PgFieldMetadata(1, "person", "id", new FakeIntegerType(), "ID").constraintScript(), 
				Matchers.equalTo(StringUtils.EMPTY)
		);
	}
}
