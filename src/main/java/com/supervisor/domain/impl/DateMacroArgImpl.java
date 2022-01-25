package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;

import com.supervisor.domain.bi.BiPeriod;
import org.apache.commons.lang.StringUtils;
import com.supervisor.domain.DateMacroArg;

public final class DateMacroArgImpl implements DateMacroArg {

	private static final String BEGIN_OF_PERIOD = "BEGIN_OF_PERIOD()";
	private static final String END_OF_PERIOD = "END_OF_PERIOD()";
	private static final String BEGIN_OF_MONTH = "BEGIN_OF_MONTH()";
	private static final String END_OF_MONTH = "END_OF_MONTH()";
	private static final String BEGIN_OF_YEAR = "BEGIN_OF_YEAR()";
	private static final String END_OF_YEAR = "END_OF_YEAR()";
	private static final String NOW_DATE = "NOW_DATE()";
	
	private final String arg;
	private final BiPeriod period;
	
	public DateMacroArgImpl(final String arg, final BiPeriod period) {
		this.arg = StringUtils.trim(arg);
		this.period = period;
	}
	
	@Override
	public boolean isValid() throws IOException {
		
		boolean isValid;
		
		switch (arg) {
			case BEGIN_OF_PERIOD:
			case END_OF_PERIOD:
			case BEGIN_OF_MONTH:
			case END_OF_MONTH:
			case BEGIN_OF_YEAR:
			case END_OF_YEAR:
			case NOW_DATE:
				isValid = true;
				break;				
			default:
				isValid = false;
				break;
		}
		
		return isValid;
	}

	@Override
	public String value() throws IOException {
		return arg;
	}

	@Override
	public LocalDate toDate() throws IOException {
		
		final LocalDate date;
		final LocalDate today = this.period.today();
		switch (arg) {
			case BEGIN_OF_PERIOD:
				date = period.begin();
				break;
			case END_OF_PERIOD:
				date = period.end();
				break;
			case BEGIN_OF_MONTH:
				date = LocalDate.of(today.getYear(), today.getMonth(), 01);
				break;
			case END_OF_MONTH:
				date = LocalDate.of(today.getYear(), today.getMonth(), today.lengthOfMonth());
				break;
			case BEGIN_OF_YEAR:
				date = LocalDate.of(today.getYear(), 01, 01);
				break;
			case END_OF_YEAR:
				date = LocalDate.of(today.getYear(), 12, 31);
				break;	
			case NOW_DATE:
				date = today;
				break;
			default:
				throw new IllegalArgumentException(String.format("%s : cette macro de date n'est pas prise en charge !", arg)); 
		}
		
		return date;
	}

}
