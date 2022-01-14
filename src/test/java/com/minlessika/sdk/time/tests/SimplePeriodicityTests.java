package com.minlessika.sdk.time.tests;

import com.minlessika.sdk.time.Periodicity;
import com.minlessika.sdk.time.PeriodicityUnit;
import com.minlessika.sdk.time.SimplePeriodicity;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

public class SimplePeriodicityTests {

	@Test
	public void daily1DayTest() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 9, 15);
		
		Periodicity daily1day = new SimplePeriodicity(1, PeriodicityUnit.DAILY, myDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				daily1day.begin(myDate), 
				Matchers.equalTo(myDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				daily1day.end(myDate), 
				Matchers.equalTo(myDate)
		);
	}
	
	@Test
	public void daily2DaysTest() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 9, 15);
		
		Periodicity daily2day = new SimplePeriodicity(2, PeriodicityUnit.DAILY, myDate, false);
		MatcherAssert.assertThat(
				"Begin",
				daily2day.begin(myDate), 
				Matchers.equalTo(myDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				daily2day.end(myDate), 
				Matchers.equalTo(myDate.plusDays(1))
		);
	}

	@Test
	public void weekly1FirstDayTest() throws IOException {
		
		final LocalDate myFirstDate = LocalDate.of(2018, 9, 17);
		
		Periodicity weekly1FirstDay = new SimplePeriodicity(1, PeriodicityUnit.WEEKLY, myFirstDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				weekly1FirstDay.begin(myFirstDate), 
				Matchers.equalTo(myFirstDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				weekly1FirstDay.end(myFirstDate), 
				Matchers.equalTo(LocalDate.of(2018, 9, 23))
		);
	}
	
	@Test
	public void weekly1SecondDayTest() throws IOException {
		
		final LocalDate mySecondDate = LocalDate.of(2018, 10, 25);
		
		Periodicity weekly1SecondDay = new SimplePeriodicity(1, PeriodicityUnit.WEEKLY, LocalDate.of(2018, 10, 2), false);
		MatcherAssert.assertThat(
				"Begin",
				weekly1SecondDay.begin(mySecondDate), 
				Matchers.equalTo(LocalDate.of(2018, 10, 23))
		);
		
		MatcherAssert.assertThat(
				"End",
				weekly1SecondDay.end(mySecondDate), 
				Matchers.equalTo(LocalDate.of(2018, 10, 29))
		);
	}
	
	@Test
	public void weekly1SecondDayAfterTest() throws IOException {
		
		final LocalDate mySecondDate = LocalDate.of(2018, 10, 25);
		
		Periodicity weekly1SecondDay = new SimplePeriodicity(1, PeriodicityUnit.WEEKLY, LocalDate.of(2018, 10, 30), false);
		MatcherAssert.assertThat(
				"Begin",
				weekly1SecondDay.begin(mySecondDate), 
				Matchers.equalTo(LocalDate.of(2018, 10, 23))
		);
		
		MatcherAssert.assertThat(
				"End",
				weekly1SecondDay.end(mySecondDate), 
				Matchers.equalTo(LocalDate.of(2018, 10, 29))
		);
	}
	
	@Test
	public void weekly2SecondDayTest() throws IOException {
		
		final LocalDate mySecondDate = LocalDate.of(2018, 9, 18);
		
		Periodicity weekly1SecondDay = new SimplePeriodicity(2, PeriodicityUnit.WEEKLY, LocalDate.of(2018, 9, 11), false);
		MatcherAssert.assertThat(
				"Begin",
				weekly1SecondDay.begin(mySecondDate), 
				Matchers.equalTo(mySecondDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				weekly1SecondDay.end(mySecondDate), 
				Matchers.equalTo(LocalDate.of(2018, 10, 01))
		);
	}
	
	@Test
	public void month1Test() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 9, 17);
		
		Periodicity monthDay = new SimplePeriodicity(1, PeriodicityUnit.MONTHLY, myDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				monthDay.begin(myDate), 
				Matchers.equalTo(myDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				monthDay.end(myDate), 
				Matchers.equalTo(LocalDate.of(2018, 10, 16)) 
		);
	}
	
	@Test
	public void month1RefBeforeTest() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 9, 18);
		final LocalDate myReferenceDate = LocalDate.of(2018, 11, 17);
		
		Periodicity monthDay = new SimplePeriodicity(1, PeriodicityUnit.MONTHLY, myReferenceDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				monthDay.begin(myDate), 
				Matchers.equalTo(LocalDate.of(2018, 9, 17))
		);
		
		MatcherAssert.assertThat(
				"End",
				monthDay.end(myDate), 
				Matchers.equalTo(LocalDate.of(2018, 10, 16)) 
		);
	}
	
	@Test
	public void month2Test() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 9, 17);
		
		Periodicity monthDay = new SimplePeriodicity(2, PeriodicityUnit.MONTHLY, myDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				monthDay.begin(myDate), 
				Matchers.equalTo(myDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				monthDay.end(myDate), 
				Matchers.equalTo(LocalDate.of(2018, 11, 16)) 
		);
	}
	
	@Test
	public void year1FirstDayTest() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 1, 1);
		
		Periodicity monthDay = new SimplePeriodicity(1, PeriodicityUnit.YEARLY, myDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				monthDay.begin(myDate), 
				Matchers.equalTo(myDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				monthDay.end(myDate), 
				Matchers.equalTo(LocalDate.of(2018, 12, 31)) 
		);
	}
	
	@Test
	public void year1Test() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 9, 17);
		
		Periodicity monthDay = new SimplePeriodicity(1, PeriodicityUnit.YEARLY, myDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				monthDay.begin(myDate), 
				Matchers.equalTo(myDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				monthDay.end(myDate), 
				Matchers.equalTo(LocalDate.of(2019, 9, 16)) 
		);
	}
	
	@Test
	public void year1RefBeforeTest() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 9, 18);
		final LocalDate myReferenceDate = LocalDate.of(2019, 11, 17);
		
		Periodicity monthDay = new SimplePeriodicity(1, PeriodicityUnit.YEARLY, myReferenceDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				monthDay.begin(myDate), 
				Matchers.equalTo(LocalDate.of(2017, 11, 17))
		);
		
		MatcherAssert.assertThat(
				"End",
				monthDay.end(myDate), 
				Matchers.equalTo(LocalDate.of(2018, 11, 16)) 
		);
	}
	
	@Test
	public void year2Test() throws IOException {
		
		final LocalDate myDate = LocalDate.of(2018, 9, 17);
		
		Periodicity monthDay = new SimplePeriodicity(2, PeriodicityUnit.YEARLY, myDate, false);
		
		MatcherAssert.assertThat(
				"Begin",
				monthDay.begin(myDate), 
				Matchers.equalTo(myDate)
		);
		
		MatcherAssert.assertThat(
				"End",
				monthDay.end(myDate), 
				Matchers.equalTo(LocalDate.of(2020, 9, 16)) 
		);
	}
}
