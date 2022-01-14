package com.minlessika.membership.utils;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.time.PrettyTimeOf;
import com.minlessika.sdk.time.TimePrinter;
import com.minlessika.sdk.time.ZoneDateTimeOf;

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
