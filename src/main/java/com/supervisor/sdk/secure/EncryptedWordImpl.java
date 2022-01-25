package com.supervisor.sdk.secure;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public final class EncryptedWordImpl implements EncryptedWord {

	private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    
	private final String securedWord;
	private final String salt;
	
	public EncryptedWordImpl(final String unsecuredWord) {
		this.salt = getSalt(30);
		this.securedWord = generateSecureWord(unsecuredWord, salt);
	}
	
	public EncryptedWordImpl(final String word, final String salt, boolean secure) {
		this.salt = salt;
		if(secure)
			this.securedWord = word;
		else			
			this.securedWord = generateSecureWord(word, salt);
	}
	
	@Override
	public String value() {
		return securedWord;
	}

	@Override
	public String salt() {
		return salt;
	}

	@Override
	public boolean verify(String word, boolean secure) {
		boolean returnValue = false;
        
        // Generate New secure password with the same salt
        String newSecureWord;
        
        if(secure)
        	newSecureWord = word;
        else
        	newSecureWord = generateSecureWord(word, salt);
        
        // Check if two passwords are equal
        returnValue = newSecureWord.equalsIgnoreCase(securedWord);
        
        return returnValue;
	}

	private static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
	
	private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
	
	private static String generateSecureWord(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
 
        returnValue = Base64.getEncoder().encodeToString(securePassword);
 
        return returnValue;
    }
}
