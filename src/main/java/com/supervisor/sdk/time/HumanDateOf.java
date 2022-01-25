package com.supervisor.sdk.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public final class HumanDateOf implements TimePrinter {

	private final LocalDate date;
	private final Locale locale;
	private final FormatStyle style;
	
	public HumanDateOf(final LocalDate date, final Locale locale) {
		this(date, locale, FormatStyle.MEDIUM);
	}
	
	public HumanDateOf(final LocalDate date, final Locale locale, final FormatStyle style) {
		this.date = date;
		this.locale = locale;
		this.style = style;
	}
	
	@Override
	public String toString() {
		return date.format(DateTimeFormatter.ofLocalizedDate(style).withLocale(locale));
	}
}
