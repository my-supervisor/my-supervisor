package com.minlessika.membership.domain;

import com.minlessika.sdk.secure.EncryptedWord;
import com.minlessika.sdk.secure.EncryptedWordImpl;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class EncryptedPasswordTest {

	@Test
	public void generateSecurePasswordTest() {
		String myPassword = "admin";
		final String salt = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";
		EncryptedWord mySecuredPassword = new EncryptedWordImpl(myPassword, salt, false);
		
		MatcherAssert.assertThat(
				mySecuredPassword.value(), 
				Matchers.equalTo("bLHHYH9H9uYbHSc4eNIz/XpJj/1ieARJf1taeqMpdxA=")
		);
	}

}
