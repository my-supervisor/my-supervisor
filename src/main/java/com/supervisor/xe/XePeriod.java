package com.supervisor.xe;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javax.json.Json;
import javax.json.JsonStructure;
import com.supervisor.domain.bi.BiPeriod;
import org.takes.rs.RsJson;

public final class XePeriod implements RsJson.Source {

	private final BiPeriod origin;
	
	public XePeriod(final BiPeriod origin) {
		this.origin = origin;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return Json.createObjectBuilder()
				.add("begin", origin.begin().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .add("end", origin.end().format(DateTimeFormatter.ISO_LOCAL_DATE))
				.add("today", origin.today().format(DateTimeFormatter.ISO_LOCAL_DATE))
				.build();
	}
}
