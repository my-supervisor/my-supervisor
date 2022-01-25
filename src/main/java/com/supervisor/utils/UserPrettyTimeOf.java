package com.supervisor.utils;

import com.supervisor.domain.User;
import com.supervisor.sdk.time.PrettyTimeOf;
import com.supervisor.sdk.time.TimePrinter;
import com.supervisor.sdk.time.ZoneDateTimeOf;

import java.io.IOException;
import java.time.LocalDateTime;

public final class UserPrettyTimeOf implements TimePrinter {
	
	private final TimePrinter printer;
	
	public UserPrettyTimeOf(final LocalDateTime date, final User user) throws IOException {
		printer = new PrettyTimeOf(new ZoneDateTimeOf(date, user.timeZone()).time(), user.locale());
	}
	
	@Override
	public String toString() {
		return printer.toString();
	}
}
