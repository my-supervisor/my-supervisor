package com.minlessika.sdk.validations;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public final class IsRequired extends AbstractValidator {

	private final String value;
	
	public IsRequired(final String value, String msg) {
		super(msg);
		
		this.value = value;
	}

	@Override
	protected boolean isNotCorrect() throws IOException {
		return StringUtils.isBlank(value);
	}
}
