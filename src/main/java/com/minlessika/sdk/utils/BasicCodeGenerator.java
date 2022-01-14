package com.minlessika.sdk.utils;

import java.io.IOException;

public class BasicCodeGenerator implements CodeGenerator {

	private final CodeContainer container;
	private final String phrase;
	
	public BasicCodeGenerator(final CodeContainer container, final String phrase) {
		this.container = container;
		this.phrase = phrase;
	}
	
	@Override
	public String generate() throws IOException {
		// newcode
		String code = new BasicAcronym(phrase, 2, 4).get();
		
		if(container.contains(code)) {
			String genericCode;
			int i = 0;
			do {
				i++;
				genericCode = String.format("%s%s", code, i);
			} while(container.contains(genericCode));	
			
			code = genericCode;
		}		
		
		return code;
	}

}
