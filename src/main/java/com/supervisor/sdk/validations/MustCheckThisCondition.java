package com.supervisor.sdk.validations;

import java.io.IOException;

public final class MustCheckThisCondition extends AbstractValidator {

	private final boolean condition;
	
	public MustCheckThisCondition(final boolean condition, String msg) {
		super(msg);

		this.condition = condition;
	}

	@Override
	protected boolean isNotCorrect() throws IOException {
		return !condition;
	}

}
