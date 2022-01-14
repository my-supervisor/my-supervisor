package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgType;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.ValueArg;

public final class PxValueArg extends DomainRecordable<ValueArg> implements ValueArg {

	private final ExpressionArg origin;
	
	public PxValueArg(final ExpressionArg origin) throws IOException {
		super(origin.listOf(ValueArg.class).get(origin.id()));
		
		this.origin = origin;
	}

	@Override
	public String name() throws IOException {
		return value();
	}

	@Override
	public FormularExpression expression() throws IOException {
		return origin.expression();
	}

	@Override
	public int no() throws IOException {
		return origin.no();
	}

	@Override
	public ExpressionArgType type() throws IOException {
		return origin.type();
	}

	@Override
	public String value() throws IOException {
		return record.valueOf(ValueArg::value);
	}

	@Override
	public DataFieldType valueType() throws IOException {
		return record.valueOf(ValueArg::valueType);
	}

	@Override
	public void update(String value, DataFieldType valueType) throws IOException {
		origin.update(value, valueType);
	}

	@Override
	public void update(EditableDataField field) throws IOException {
		origin.update(field); 
	}

	@Override
	public void update(ParamDataField param) throws IOException {
		origin.update(param); 
	}

	@Override
	public void update(FormularExpression expression) throws IOException {
		origin.update(expression); 
	}

	@Override
	public void update(FormularDataField formular) throws IOException {
		origin.update(formular); 
	}

}
