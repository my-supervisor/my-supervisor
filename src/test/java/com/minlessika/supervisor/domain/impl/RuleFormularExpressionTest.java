package com.minlessika.supervisor.domain.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class RuleFormularExpressionTest {

	@Test
	public void testBeginAt$() {
		Pattern pattern = Pattern.compile("^\\$(.*?)");
		Matcher matcher = pattern.matcher("$E1");
		
		MatcherAssert.assertThat(matcher.find(), Matchers.is(true));
	}

	@Test
	public void testBeginAtDieze() {
		Pattern pattern = Pattern.compile("^#(.*?)");
		Matcher matcher = pattern.matcher("#E1222");
		
		MatcherAssert.assertThat(matcher.find(), Matchers.is(true));
	}
}
