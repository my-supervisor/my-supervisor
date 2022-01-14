package com.minlessika.sdk.time;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

public final class PrettyTimeOf implements TimePrinter {

	private final ZonedDateTime date;
	private final Locale locale;
	
	public PrettyTimeOf(final ZonedDateTime date, final Locale locale) {
		this.date = date;
		this.locale = locale;
	}
	
	@Override
	public String toString() {
		return new PrettyTime(locale).format(Date.from(date.toInstant()));
	}
}
