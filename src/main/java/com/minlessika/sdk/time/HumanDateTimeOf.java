package com.minlessika.sdk.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public final class HumanDateTimeOf implements TimePrinter {

	private final ZonedDateTime date;
	private final Locale locale;
	private final FormatStyle style;
	
	public HumanDateTimeOf(final ZonedDateTime date, final Locale locale) {
		this(date, locale, FormatStyle.MEDIUM);
	}
	
	public HumanDateTimeOf(final ZonedDateTime date, final Locale locale, final FormatStyle style) {
		this.date = date;
		this.locale = locale;
		this.style = style;
	}
	
	@Override
	public String toString() {
		return date.format(DateTimeFormatter.ofLocalizedDateTime(style).withLocale(locale));
	}
}
