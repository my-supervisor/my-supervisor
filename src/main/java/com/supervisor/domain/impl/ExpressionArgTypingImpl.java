package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionArgType;
import com.supervisor.domain.ExpressionArgTyping;

public final class ExpressionArgTypingImpl implements ExpressionArgTyping {

	private final Record<ExpressionArg> record;
	
	public ExpressionArgTypingImpl(final Record<ExpressionArg> record) {
		this.record = record;
	}
	
	@Override
	public ExpressionArg argument() throws IOException {
		
		final ExpressionArg arg;
		
		ExpressionArgType type = record.valueOf(ExpressionArg::type);
		final ExpressionArg origin = new PxExpressionArg(record);
		
		switch (type) {
			case DATA_FIELD:
				arg = new PxEditableDataFieldArg(origin);
				break;
			case EXPRESSION:
				arg = new PxExpressionValueArg(origin);
				break;
			case FORMULAR:
				arg = new PxFormularArg(origin);
				break;
			case PARAMETER:
				arg = new PxParamArg(origin);
				break;
			case VALUE:
				arg = new PxValueArg(origin);
				break;
			default:
				throw new IllegalArgumentException(String.format("Ce type d'argument (%s) n'est pas pris en charge !", type.toString()));
		}
		
		return arg;
	}

}
