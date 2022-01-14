package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.utils.ListOfUniqueRecord;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldArg;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionValueArg;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularExpressionType;

public abstract class PxBasicFormularExpression extends DomainRecordable<FormularExpression> implements FormularExpression {

	public PxBasicFormularExpression(final Record<FormularExpression> record) throws IOException {
		super(record);
	}
	
	@Override
	public FormularDataField formular() throws IOException {
		final Long formularId = record.valueOf(FormularExpression::formular);
		return new PxFormularDataField(record.of(DataField.class, formularId));
	}

	@Override
	public List<DataField> dataFieldArgs() throws IOException {
		
		List<DataField> fields = new ListOfUniqueRecord<>();
		
		for (ExpressionArg arg : arguments().items()) {
			fields.addAll(dataFieldsOf(arg));
		}

		return fields;
	}
	
	private static List<DataField> dataFieldsOf(final ExpressionArg arg) throws IOException {
		
		final List<DataField> fields = new ListOfUniqueRecord<>();
		
		switch (arg.type()) {
			case DATA_FIELD:
			case FORMULAR:
			case PARAMETER:
				DataFieldArg dArg = (DataFieldArg)arg;
				fields.add(dArg.field());
				break;
			case EXPRESSION:
				ExpressionValueArg eArg = (ExpressionValueArg)arg;
				fields.addAll(eArg.targetExpr().dataFieldArgs());
				break;	
			default:
				break;
		}
		
		return fields;
	}

	@Override
	public String name() throws IOException {
		return String.format("E%s", no());
	}
	
	@Override
	public int no() throws IOException {
		return record.valueOf(FormularExpression::no);
	}

	@Override
	public void order(int no) throws IOException {
		
		record.entryOf(FormularExpression::no, no)
		  	  .update();
	}

	@Override
	public FormularExpressionType type() throws IOException {
		return record.valueOf(FormularExpression::type);
	}
}
