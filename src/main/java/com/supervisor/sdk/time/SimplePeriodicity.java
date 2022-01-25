package com.supervisor.sdk.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class SimplePeriodicity implements Periodicity {

	final int number;
	final PeriodicityUnit unit;
	final LocalDate reference;
	final boolean closeInterval;
	
	public SimplePeriodicity(final int number, final PeriodicityUnit unit, final LocalDate reference, final boolean closeInterval) {
		
		if(number <= 0)
			throw new IllegalArgumentException("Vous devez saisir un entier supérieur à 0 !");
		
		this.number = number;		
		this.unit = unit;
		this.reference = reference;
		this.closeInterval = closeInterval;
	}
	
	@Override
	public int number() {
		return number;
	}

	@Override
	public PeriodicityUnit unit() {
		return unit;
	}

	@Override
	public LocalDate begin(LocalDate today) {
		LocalDate date;
		
		switch (unit) {
			case DAILY:
				if(number == 1)
					date = today;
				else {
					Long days = ChronoUnit.DAYS.between(reference, today);
					Long rest = days % number;
					date = today.plusDays(rest);
				}				
				break;
			case WEEKLY:				
				date = today.with(reference.getDayOfWeek());
				break;
			case MONTHLY:
				LocalDate referenceOfYear = LocalDate.of(today.getYear(), reference.getMonthValue(), reference.getDayOfMonth());
				
				int numberSigned = number;
				
				boolean isUpper = referenceOfYear.isAfter(today);
				if(closeInterval)
					isUpper = isUpper || referenceOfYear.isEqual(today);
				
				if(isUpper)
					numberSigned = -number;
				
				boolean belongToPeriod = false;
				LocalDate referenceOfToday = referenceOfYear;
				do {
					referenceOfToday = referenceOfToday.plusMonths(numberSigned);
					
					if(numberSigned < 0) {
						belongToPeriod = referenceOfToday.isBefore(today);						
					}
					else
						{
							belongToPeriod = referenceOfToday.isAfter(today);
							if(closeInterval)
								belongToPeriod = belongToPeriod || referenceOfToday.isEqual(today);
							
							if(belongToPeriod)
								referenceOfToday = referenceOfToday.minusMonths(numberSigned);
						}
					
				}while(!belongToPeriod);
				
				date = referenceOfToday;				
				break;
			case YEARLY:
				LocalDate referenceOfYearY = LocalDate.of(today.getYear(), reference.getMonthValue(), reference.getDayOfMonth());
				int numberSignedY = number;
				
				boolean isUpperY = referenceOfYearY.isAfter(today);
				if(closeInterval)
					isUpperY = isUpperY || referenceOfYearY.isEqual(today);
				
				if(isUpperY)
					numberSignedY = -number;
				
				boolean belongToPeriodY = false;
				LocalDate referenceOfTodayY = referenceOfYearY;
				do {
					referenceOfTodayY = referenceOfTodayY.plusYears(numberSignedY);
					
					if(numberSignedY < 0)
						belongToPeriodY = referenceOfTodayY.isBefore(today);
					else {
						belongToPeriodY = referenceOfTodayY.isAfter(today);
						if(closeInterval)
							belongToPeriodY = belongToPeriodY || referenceOfTodayY.isEqual(today);
						
						if(belongToPeriodY)
							referenceOfTodayY = referenceOfTodayY.minusYears(numberSignedY);
					}
					
				}while(!belongToPeriodY);
				
				date = referenceOfTodayY;				
				break;
			case FOREVER:
				date = LocalDate.of(1970, 01, 01);  
				break;
			default:
				throw new IllegalArgumentException("Date de début de période : vous devez définir une unité de période !");
		}
		
		return date;
	}
	
	@Override
	public LocalDate end(LocalDate today) {		
		
		LocalDate date;
		LocalDate begin = begin(today);
		
		switch (unit) {
			case DAILY:
				date = begin.plusDays(number).minusDays(1);
				break;
			case WEEKLY:
				date = begin.plusDays(7L * number).minusDays(1);
				break;
			case MONTHLY:
				date = begin.plusMonths(number).minusDays(1);
				break;
			case YEARLY:
				date = begin.plusYears(number).minusDays(1);
				break;
			case FOREVER:
				date = today; 
				break;
			default:
				throw new IllegalArgumentException("Date de fin de période : vous devez définir une unité de période !");
		}
			
		if(closeInterval)
			date = date.plusDays(1);
		
		return date;
	}

	@Override
	public LocalDate reference() {
		return reference;
	}

	@Override
	public boolean closeInterval() {
		return closeInterval;
	}
}
