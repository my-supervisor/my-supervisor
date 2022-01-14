package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.FormularCondition;
import com.minlessika.supervisor.domain.ParamDataField;

public final class PxFormularCondition extends DomainRecordable<FormularCondition> implements FormularCondition {
	
	public PxFormularCondition(Record<FormularCondition> record) throws IOException {
		super(record);
	}

	@Override
	public ParamDataField param() throws IOException {
		final Long paramId = record.valueOf(FormularCondition::param);
		return new PxParamDataField(record.of(DataField.class, paramId));
	}

	@Override
	public Comparator comparator() throws IOException {
		return record.valueOf(FormularCondition::comparator);
	}

	@Override
	public String value() throws IOException {
		return record.valueOf(FormularCondition::value);
	}

	@Override
	public boolean isTrue() throws IOException {
		
		final boolean isTrue;
		
		String paramValue = param().value();
		String value = value();
		Comparator comparator = comparator();
		DataFieldType type = param().type();
		
		switch (type) {
			case NUMBER:
				isTrue = numberComparison(Double.valueOf(paramValue), Double.valueOf(value), comparator);
				break;
			case BOOLEAN:
				isTrue = booleanComparison(Boolean.valueOf(paramValue), Boolean.valueOf(value), comparator);
				break;
			case DATE:
				isTrue = dateComparison(LocalDate.parse(paramValue), LocalDate.parse(value), comparator);
				break;
			default:
				isTrue = stringComparison(paramValue, value, comparator);
				break;
		}
		
		return isTrue;
	}	
	
	private static boolean numberComparison(double paramValue, double value, Comparator comparator) {
		boolean isTrue = true;
		
		switch (comparator) {
			case EQUALS:
				isTrue = paramValue == value;
				break;
			case NOT_EQUALS:
				isTrue = paramValue != value;
				break;
			case GREATER_OR_EQUALS:
				isTrue = paramValue >= value;
				break;
			case LESS_OR_EQUALS:
				isTrue = paramValue <= value;
				break;
			case GREATER_THAN:
				isTrue = paramValue > value;
				break;
			case IS_ABSENT:
				isTrue = false;
				break;
			case IS_PRESENT:
				isTrue = true;
				break;
			case LESS_THAN:
				isTrue = paramValue < value;
				break;
			case BETWEEN:
				isTrue = true;
				break;
			case CONTAINS:
				isTrue = true;
				break;
			case ENDS_WITH:
				isTrue = true;
				break;
			case NOT_BETWEEN:
				isTrue = true;
				break;
			case STARTS_WITH:
				isTrue = true;
				break;
			default:
				isTrue = true;
				break;
		}
		
		return isTrue;
	}
	
	private static boolean booleanComparison(boolean paramValue, boolean value, Comparator comparator) {
		boolean isTrue = true;
		
		switch (comparator) {
			case EQUALS:
				isTrue = paramValue == value;
				break;
			case NOT_EQUALS:
				isTrue = paramValue != value;
				break;
			case GREATER_OR_EQUALS:
				isTrue = true;
				break;
			case LESS_OR_EQUALS:
				isTrue = true;
				break;
			case GREATER_THAN:
				isTrue = true;
				break;
			case IS_ABSENT:
				isTrue = false;
				break;
			case IS_PRESENT:
				isTrue = true;
				break;
			case LESS_THAN:
				isTrue = true;
				break;
			case BETWEEN:
				isTrue = true;
				break;
			case CONTAINS:
				isTrue = true;
				break;
			case ENDS_WITH:
				isTrue = true;
				break;
			case NOT_BETWEEN:
				isTrue = true;
				break;
			case STARTS_WITH:
				isTrue = true;
				break;
			default:
				isTrue = true;
				break;
		}
		
		return isTrue;
	}
	
	private static boolean dateComparison(LocalDate paramValue, LocalDate value, Comparator comparator) {
		boolean isTrue = true;
		
		switch (comparator) {
			case EQUALS:
				isTrue = paramValue == value;
				break;
			case NOT_EQUALS:
				isTrue = paramValue != value;
				break;
			case GREATER_OR_EQUALS:
				isTrue = paramValue.isAfter(value) || paramValue == value;
				break;
			case LESS_OR_EQUALS:
				isTrue = paramValue.isBefore(value) || paramValue == value;
				break;
			case GREATER_THAN:
				isTrue = paramValue.isAfter(value);
				break;
			case IS_ABSENT:
				isTrue = false;
				break;
			case IS_PRESENT:
				isTrue = true;
				break;
			case LESS_THAN:
				isTrue = paramValue.isBefore(value);
				break;
			case BETWEEN:
				isTrue = true;
				break;
			case CONTAINS:
				isTrue = true;
				break;
			case ENDS_WITH:
				isTrue = true;
				break;
			case NOT_BETWEEN:
				isTrue = true;
				break;
			case STARTS_WITH:
				isTrue = true;
				break;
			default:
				isTrue = true;
				break;
		}
		
		return isTrue;
	}
	
	private static boolean stringComparison(String paramValue, String value, Comparator comparator) {
		boolean isTrue = true;
		
		switch (comparator) {
			case EQUALS:
				isTrue = paramValue.equals(value);
				break;
			case NOT_EQUALS:
				isTrue = !paramValue.equals(value);
				break;
			case GREATER_OR_EQUALS:
				isTrue = true;
				break;
			case LESS_OR_EQUALS:
				isTrue = true;
				break;
			case GREATER_THAN:
				isTrue = true;
				break;
			case IS_ABSENT:
				isTrue = false;
				break;
			case IS_PRESENT:
				isTrue = true;
				break;
			case LESS_THAN:
				isTrue = true;
				break;
			case BETWEEN:
				isTrue = true;
				break;
			case CONTAINS:
				isTrue = paramValue.contains(value);
				break;
			case ENDS_WITH:
				isTrue = paramValue.endsWith(value);
				break;
			case NOT_BETWEEN:
				isTrue = true;
				break;
			case STARTS_WITH:
				isTrue = paramValue.startsWith(value);
				break;
			default:
				isTrue = true;
				break;
		}
		
		return isTrue;
	}

	@Override
	public double defaultValue() throws IOException {
		return record.valueOf(FormularCondition::defaultValue);
	}
}
