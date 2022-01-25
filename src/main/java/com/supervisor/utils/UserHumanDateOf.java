package com.supervisor.utils;

import com.supervisor.domain.User;
import com.supervisor.sdk.time.HumanDateOf;
import com.supervisor.sdk.time.HumanDateTimeOf;
import com.supervisor.sdk.time.TimePrinter;
import com.supervisor.sdk.time.ZoneDateTimeOf;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.FormatStyle;

public final class UserHumanDateOf implements TimePrinter {

	private final TimePrinter printer;
	
	public UserHumanDateOf(final LocalDate date, final User user) throws IOException {
		printer = new HumanDateOf(date, user.locale());
	}
	
	public UserHumanDateOf(final LocalDate date, final FormatStyle style, final User user) throws IOException {
		printer = new HumanDateOf(date, user.locale(), style);
	}
	
	public UserHumanDateOf(final LocalDateTime date, final User user) throws IOException {
		printer = new HumanDateTimeOf(new ZoneDateTimeOf(date, user.timeZone()).time(), user.locale());
	}
	
	public UserHumanDateOf(final LocalDateTime date, final FormatStyle style, final User user) throws IOException {
		printer = new HumanDateTimeOf(new ZoneDateTimeOf(date, user.timeZone()).time(), user.locale(), style);
	}
	
	@Override
	public String toString() {
		return printer.toString();
	}
}
