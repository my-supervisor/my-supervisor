package com.minlessika.sdk.tests.pgsql.conditions;

import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.datasource.conditions.Condition;
import com.supervisor.sdk.datasource.conditions.pgsql.PgSimpleCondition;
import com.minlessika.sdk.tests.metadata.MyDomain;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.io.IOException;

public class ConditionTest {

	@Test
	public void EqualsTest() throws IOException {
		
		final Condition expected = new PgSimpleCondition(
										MyDomain.class, 
										MyDomain::name, 
										Matchers.equalsTo("Ok")
								   );
		
		Condition actual = new PgSimpleCondition(
								MyDomain.class, 
								MyDomain::name, 
								Matchers.equalsTo("Ok")
							 );
		
		MatcherAssert.assertThat(
				expected.equals(actual), 
				org.hamcrest.Matchers.is(true) 
	    );
		
		actual = new PgSimpleCondition(
					MyDomain.class, 
					MyDomain::name, 
					Matchers.equalsTo("NOk")
				 );
		
		MatcherAssert.assertThat(
				expected.equals(actual), 
				org.hamcrest.Matchers.is(false) 
	    );
	}

}
