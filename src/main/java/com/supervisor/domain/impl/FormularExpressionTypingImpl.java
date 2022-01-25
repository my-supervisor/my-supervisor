package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.FormularExpressionTyping;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExpressionType;

public final class FormularExpressionTypingImpl implements FormularExpressionTyping {

	private final Record<FormularExpression> record;
	
	public FormularExpressionTypingImpl(final Record<FormularExpression> record) {
		this.record = record;
	}
	
	@Override
	public FormularExpression expression() throws IOException {
		
		final FormularExpressionType type = record.valueOf(FormularExpression::type);
		
		final FormularExpression expr;
		switch (type) {
			case CASE:
				expr = new PxFormularCaseExpression(record);
				break;
			case EXTENDED_TO_PARENT:
				expr = new PxFormularExtendedToParentExpression(record);
				break;
			case EXTENDED_TO_CHILD:
				expr = new PxFormularExtendedToChildExpression(record);
				break;
			case EXTENDED_TO_MODEL:
				expr = new PxFormularExtendedToModelExpression(record);
				break;
			case SIMPLE:
				expr = new PxFormularSimpleExpression(record);
				break;
			default:
				throw new IllegalArgumentException(String.format("Formular type expression : %s not supported !", type.toString()));
		}

		return expr;
	}

}
