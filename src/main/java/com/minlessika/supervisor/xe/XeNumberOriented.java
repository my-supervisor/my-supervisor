package com.minlessika.supervisor.xe;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonStructure;

import org.takes.rs.RsJson;

import com.minlessika.supervisor.indicator.NumberOriented;

public final class XeNumberOriented implements RsJson.Source {

	private final NumberOriented origin;
	
	public XeNumberOriented(final NumberOriented origin) {
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
                .add("short_name", origin.type().shortName())
                .add("description", origin.description())
                .add("label", origin.label())
				.add("number", origin.number())
				.add("increaseRate", origin.increaseRate())
				.add("increaseInPercent", origin.increaseInPercent())
				.add("manageEvolutionPercent", origin.manageEvolutionPercent())
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
