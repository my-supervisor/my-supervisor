package com.supervisor.takes;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.takes.Request;
import org.takes.rq.RqHref;

public final class IndicatorSource {
	
	public static String url(final Request req) throws IOException {
		
		String source = new RqHref.Smart(req).single("source");
		String url;
		
		if(StringUtils.contains(source, "activity")) {
			url = String.format("/home?activity=%s", StringUtils.remove(source, "activity"));
		}else {
			switch (source) {
				case "indicator":
					url = String.format("/indicator/list");
					break;
				case "template":
					url = String.format("/indicator/template");
					break;
				default:
					throw new IllegalArgumentException(String.format("La source %s n'est pas prise en charge !", source));
			}
		}
			
		return url;
	}
}
