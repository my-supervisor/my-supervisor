package com.minlessika.sdk.utils;

import org.apache.commons.lang.StringUtils;

public class BasicAcronym implements Acronym {

	private final String phrase;
	private final int min;
	private final int max;
	
	public BasicAcronym(final String phrase, final int min, final int max) {
		this.phrase = StringUtils.trim(phrase);
		this.min = min;
		this.max = max;		
	}
	
	@Override
	public String get() {
		
		if(StringUtils.isBlank(phrase))
			throw new IllegalArgumentException("Acronym : phrase to abreviate cannot be empty !");
		
		final String cleanedPhrase = phrase.replaceAll("[^a-zA-Z0-9]", "");
		final int utilLength = StringUtils.length(cleanedPhrase);
		
		if(utilLength < min)
			throw new IllegalArgumentException(String.format("Acronym : length of util letters of the phrase (%s) must be upper or equals to %s !", cleanedPhrase, min));		
		
		final String acronym;
		if(utilLength < max)
			acronym = cleanedPhrase.substring(0, utilLength); 
		else
			acronym = cleanedPhrase.substring(0, max); 
		
        return acronym.toUpperCase();
	}
	
}
