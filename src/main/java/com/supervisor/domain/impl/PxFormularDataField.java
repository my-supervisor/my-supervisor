package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.Arrays;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.datasource.comparators.Matchers;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.FormularCondition;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExpressions;
import com.supervisor.domain.ParamDataField;

public final class PxFormularDataField extends PxDataField implements FormularDataField {

	private final Record<FormularDataField> formularRecord;
	
	public PxFormularDataField(final Record<DataField> record) throws IOException {
		super(record);
		
		this.formularRecord = record.listOf(FormularDataField.class).get(record.id());
	}
	
	@Override
	protected void checkStyle(Record<DataField> record) throws IOException {
		if(record.valueOf(DataField::style) != DataFieldStyle.FORMULAR)
			throw new IllegalArgumentException("Field isn't a formular field !");
	}
	
	@Override
	public AggregatedModel model() throws IOException {
		return (AggregatedModel)super.model();
	}

	@Override
	public FormularExpressions expressions() throws IOException {
		return new PxFormularExpressions(this);
	}

	@Override
	public FormularExpression mainExpression() throws IOException {
		return expressions().last();
	}

	@Override
	public FormularExpressions previousExpressions(FormularExpression expr) throws IOException {
		return expressions().where(FormularExpression::no, Matchers.lessThan(expr.no()));
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {
		throw new UnsupportedOperationException("FormularDataField::update");
	}
	
	@Override
	public void update(String name, String code, DataFieldType type) throws IOException {
		
		if(type == DataFieldType.TABLE) {
			throw new IllegalArgumentException("Champ de données simple attendu !"); 
		}
		
		super.update(code, name, type, DataFieldStyle.FORMULAR, StringUtils.EMPTY);
	}

	@Override
	public FormularCondition condition() throws IOException {
		
		RecordSet<FormularCondition> conditionSource = formularRecord.listOf(FormularCondition.class);
		
		if(conditionSource.contains(id()))
			return new PxFormularCondition(conditionSource.get(id()));
		else
			return FormularCondition.EMPTY;
	}

	@Override
	public void addCondition(ParamDataField param, Comparator comparator, String value, double defaultValue) throws IOException {
		
		RecordSet<FormularCondition> conditionSource = formularRecord.listOf(FormularCondition.class);
		
		String adaptedValue = PxModelFilterCondition.adaptValue(param.type(), value, Arrays.asList());
		PxModelFilterCondition.validateType(param.type(), adaptedValue, Arrays.asList());
		
		if(conditionSource.contains(id())) {
			Record<FormularCondition> rec = conditionSource.get(id());
			rec.entryOf(FormularCondition::param, param.id())
	           .entryOf(FormularCondition::comparator, comparator)
	           .entryOf(FormularCondition::value, adaptedValue)
	           .entryOf(FormularCondition::defaultValue, defaultValue)
	           .update();
		} else {
			conditionSource.entryOf(FormularCondition::id, id())
						   .entryOf(FormularCondition::param, param.id())
				           .entryOf(FormularCondition::comparator, comparator)
				           .entryOf(FormularCondition::value, adaptedValue)
				           .entryOf(FormularCondition::defaultValue, defaultValue)
				           .add();
		}
	}

	@Override
	public void removeCondition() throws IOException {
		RecordSet<FormularCondition> conditionSource = formularRecord.listOf(FormularCondition.class);
		conditionSource.remove(id()); 		
	}

	@Override
	public DataFieldDependencies dependencies() throws IOException {
		return new PgFormularDependencies(this);
	}
}
