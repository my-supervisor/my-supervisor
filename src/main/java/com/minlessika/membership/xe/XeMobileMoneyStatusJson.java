package com.minlessika.membership.xe;

import org.apache.commons.lang.StringUtils;
import org.takes.rs.RsJson;

import javax.json.Json;
import javax.json.JsonStructure;
import java.io.IOException;

public final class XeMobileMoneyStatusJson implements RsJson.Source {

	private final String code;
	private final String message;
	private final String transactionId;
	
	public XeMobileMoneyStatusJson(final String code, final String message) {
		this(code, message, StringUtils.EMPTY);
	}
	
	public XeMobileMoneyStatusJson(final String code, final String message, final String transactionId) {
		this.code = code;
		this.message = message;
		this.transactionId = transactionId;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return  Json.createObjectBuilder()
					.add("message", message)
	                .add("code", code)
	                .add("transactionId", transactionId) 
					.build();
	}
}
