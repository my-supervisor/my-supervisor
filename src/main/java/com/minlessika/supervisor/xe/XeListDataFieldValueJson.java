package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;

import org.takes.rs.RsJson;

import com.minlessika.supervisor.domain.bi.BiResult;
import com.minlessika.supervisor.domain.bi.BiRow;

public final class XeListDataFieldValueJson implements RsJson.Source {

	private final String key;
	private final BiResult result;
	
	public XeListDataFieldValueJson(final String key, final BiResult result) {
		this.key = key;
		this.result = result;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		if(result == BiResult.EMPTY) {
			return  Json.createObjectBuilder()
						.add("items", Json.createArrayBuilder().build())
		                .add("count", 0)
						.build();
		} else {
			return  Json.createObjectBuilder()
						.add("items", templatesJson(key, result.rows()))
		                .add("count", result.count())
						.build();
		}
		
	}
	
	private static JsonArray templatesJson(final String key, final List<BiRow> rows) throws IOException {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		
		for (BiRow row : rows) {
			
			final Object value = row.get(key).value();
			
			if(value instanceof Boolean) {
				builder.add(Json.createObjectBuilder()
						.add("id", (String)row.get("REF").value())
		                .add("value", (Boolean)row.get(key).value())
               );
			} else if(value instanceof java.sql.Date) {
				final LocalDate date = ((java.sql.Date)row.get(key).value()).toLocalDate();
				builder.add(Json.createObjectBuilder()
					   .add("id", (String)row.get("REF").value())
		               .add("value", date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))
               );
			} else if(value instanceof Double) {
				final Double number = row.get(key).value();
				builder.add(Json.createObjectBuilder()
					   .add("id", (String)row.get("REF").value())
		               .add("value", number)
               );
			} else {
				builder.add(Json.createObjectBuilder()
						.add("id", (String)row.get("REF").value())
		                .add("value", (String)row.get(key).value())
               );
			}
		}
		
		return builder.build();
	}
}
