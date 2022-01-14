package com.minlessika.sdk.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public final class ZoneDateTimeOf {

	private final LocalDateTime date;
	private final ZoneId zoneId;
	
	public ZoneDateTimeOf(final LocalDateTime date, TimeZone timeZone) {
		this(date, timeZone.toZoneId());
	}
	
	public ZoneDateTimeOf(final LocalDateTime date, ZoneId zoneId) {
		this.date = date;
		this.zoneId = zoneId;
	}
	
	public ZonedDateTime time() {
		return date.atZone(zoneId);
	}
}
