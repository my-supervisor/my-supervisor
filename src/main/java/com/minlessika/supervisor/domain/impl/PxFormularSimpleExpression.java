package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgs;
import com.minlessika.supervisor.domain.FormularFunc;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularSimpleExpression;

public final class PxFormularSimpleExpression extends PxBasicFormularExpression implements FormularSimpleExpression {

	private final Record<FormularSimpleExpression> record;
	
	public PxFormularSimpleExpression(Record<FormularExpression> record) throws IOException {
		super(record);
		
		this.record = record.listOf(FormularSimpleExpression.class).get(record.id());
	}

	@Override
	public FormularFunc func() throws IOException {
		return record.valueOf(FormularSimpleExpression::func);
	}

	@Override
	public ExpressionArgs arguments() throws IOException {
		return new PxSimpleExpressionArgs(this);
	}

	@Override
	public void update(FormularFunc func) throws IOException {
		
		record.mustCheckThisCondition(
				func != FormularFunc.NONE, 
				"Vous devez spécifier une fonction !"
		);
		
		FormularFunc oldFunc = func();
		
		record.entryOf(FormularSimpleExpression::func, func)
	          .update();
		
		if(oldFunc.nbArgs() == func.nbArgs())
			return;
		
		if(func == FormularFunc.FIRST || func == FormularFunc.PREVIOUS || func == FormularFunc.NEXT) {
			arguments().remove();
			arguments().addDataFieldArg(1);			
		}else {
			if(func.nbArgs() == 0) {
				arguments().remove();
			}else {
				if(func.nbArgs() > oldFunc.nbArgs()) {
					for (int i = oldFunc.nbArgs() + 1; i <= func.nbArgs(); i++) {
						arguments().add(i);
					}
				}else {
					List<ExpressionArg> arguments = arguments().items();
					for (int i = oldFunc.nbArgs() - 1; i >= func.nbArgs(); i--) {
						arguments().remove(arguments.get(i));
					}			
				}
			}			
		}		
	}

	@Override
	public String toText() throws IOException {
		
		String argumentStr = StringUtils.EMPTY;
		for (ExpressionArg arg : arguments().items()) {
			if(StringUtils.isBlank(argumentStr)) {
				argumentStr = arg.name();
			}else {
				argumentStr = String.format("%s, %s", argumentStr, arg.name());
			}
		}
		
		return String.format("%s (%s)", func().name(), argumentStr);
	}
	
	@Override
	public ExpressionArg arg(int position) throws IOException {
		return arguments().where(ExpressionArg::no, position).first();
	}
}
