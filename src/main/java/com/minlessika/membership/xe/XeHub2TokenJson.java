package com.minlessika.membership.xe;

import org.takes.rs.RsJson;

import javax.json.Json;
import javax.json.JsonStructure;
import java.io.IOException;

public final class XeHub2TokenJson implements RsJson.Source {

	private final String tuid;
	
	public XeHub2TokenJson(final String tuid) {
		this.tuid = tuid;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return  Json.createObjectBuilder()
					.add("tuid", tuid)
					.build();
	}
}
