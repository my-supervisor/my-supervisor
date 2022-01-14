package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Tax;
import com.minlessika.membership.billing.TaxType;
import com.minlessika.membership.billing.TaxValueType;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.impl.UserAdmin;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.utils.logging.Logger;
import com.minlessika.sdk.utils.logging.MLogger;

import java.io.IOException;

public final class PxTax extends DomainRecordable<Tax> implements Tax {

	private static final Logger logger = new MLogger(PxTax.class);
	
	public PxTax(Record<Tax> record) throws IOException {
		super(record);
	}

	@Override
	public TaxType type() throws IOException {
		return record.valueOf(Tax::type);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Tax::name);
	}

	@Override
	public String shortName() throws IOException {
		return record.valueOf(Tax::shortName);
	}

	@Override
	public double value() throws IOException {
		return record.valueOf(Tax::value);
	}

	@Override
	public TaxValueType valueType() throws IOException {
		return record.valueOf(Tax::valueType);
	}

	@Override
	public double decimalValue() throws IOException {
		if(valueType() == TaxValueType.PERCENT)
			return value() / 100.0;
		else
			return value();
	}

	@Override
	public String valueToString() throws IOException {
		String summary = "";
		
		switch (valueType()) {
		case PERCENT:
			summary = String.format("%.2f", value()) + " %";
			break;
		case AMOUNT:
			Currency currency = new UserAdmin(base()).preferredCurrency();
			summary = currency.toString(value());
			break;
		default:
			break;
		}
		
		return summary;
	}

	@Override
	public void update(String name, String shortName, TaxType type, double value, TaxValueType valueType)
			throws IOException {
		
		record.isRequired(Tax::name, name);
		record.isRequired(Tax::shortName, shortName);
		
		record.mustBeUnique(Tax::shortName, shortName);
		
		record.entryOf(Tax::name, name)
		      .entryOf(Tax::shortName, shortName)
		      .entryOf(Tax::type, type)
		      .entryOf(Tax::value, value)
		      .entryOf(Tax::valueType, valueType)
		      .update();
		
	}

	@Override
	public double evaluateAmount(double amountHt) throws IOException {
		
		double amount = 0;
		
		switch (valueType()) {
			case PERCENT:
				amount = decimalValue() * amountHt;
				break;
			case AMOUNT:
				amount = value();
				break;
			default:
				break;
		}
		
		return amount;
	}
	
	@Override
	public String toString(){
		try {
			return String.format("%s (%s)", shortName(), valueToString());
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

}
