package com.minlessika.supervisor.xe;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonStructure;

import org.takes.rs.RsJson;

import com.minlessika.supervisor.indicator.GoalNumber;

public final class XeGoalNumber implements RsJson.Source {

	private final GoalNumber origin;
	
	public XeGoalNumber(final GoalNumber origin) {
		this.origin = origin;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return Json.createObjectBuilder()
				.add("id", origin.id())
                .add("code", origin.code())
                .add("name", origin.name())
                .add("type", origin.type().toString())
                .add("type_id", origin.type().name())
                .add("color", origin.color().toString())
                .add("color_id", origin.color().name())
                .add("color_code", origin.color().code())
                .add("short_name", origin.type().shortName())
                .add("description", origin.description())
                .add("label", origin.label())
				.add("number", origin.number())
				.add("goal", origin.goal())
				.add("numberInPercent", origin.numberInPercent())
				.add("unitySymbol", origin.unitySymbol())
				.add("symbolPosition", origin.symbolPosition().name())
				.add("activity_id", origin.activity().id())
                .add("activity", origin.activity().name())
                .add("sizeX", origin.sizeX())
                .add("sizeY", origin.sizeY())
                .add("row", origin.row())
                .add("col", origin.col())
				.build();
	}
}
