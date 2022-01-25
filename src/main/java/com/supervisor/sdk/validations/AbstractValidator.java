package com.supervisor.sdk.validations;

import java.io.IOException;

public abstract class AbstractValidator implements Validator {

	private final String msg;
	
	public AbstractValidator(final String msg) {
		this.msg = msg;
	}
	
	protected abstract boolean isNotCorrect() throws IOException;
	
	public void validate() throws IOException {
		if(isNotCorrect())
			throw new IllegalArgumentException(msg);
	}

}
