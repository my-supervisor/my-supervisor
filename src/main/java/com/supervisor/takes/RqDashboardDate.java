package com.supervisor.takes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

public final class RqDashboardDate implements DashboardDate {

	final Request req;
	
	public RqDashboardDate(Request req) {
		this.req = req;
	}
	
	@Override
	public LocalDate toLocalDate() throws IOException {
		Smart params = new RqHref.Smart(req);
		
		String dateStr = params.single("date", "NONE");
		LocalDate date = LocalDate.now();
		if(!dateStr.equals("NONE")) {						
			try {
				date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
			} catch (Exception e) {
				throw new IllegalArgumentException("Le format de la date n'est pas correct !");
			}
		}
		
		return date;
	}

}
