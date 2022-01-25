package com.minlessika.sdk.tests.mailing;

import com.supervisor.sdk.mailing.BasicMailing;
import org.hamcrest.MatcherAssert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class BasicMailingTest {

	@Ignore
	@Test
	public void SendTest() {
		
		try {
			new BasicMailing().send("baudolivier.oura@gmail.com", "Test", "Test");
			MatcherAssert.assertThat("Send test passed", true);
		} catch (IOException e) {
			MatcherAssert.assertThat(e.getLocalizedMessage(), false);
		}
		
		
	}

}
