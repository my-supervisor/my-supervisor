package com.supervisor.xe;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonStructure;

import org.takes.rs.RsJson;

import com.supervisor.indicator.DynamicTable2Col;

public final class XeDynamicTable2Col implements RsJson.Source {

	private final DynamicTable2Col origin;
	
	public XeDynamicTable2Col(final DynamicTable2Col origin) {
		this.origin = origin;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return Json.createObjectBuilder()
				.add("id", origin.id())
                .add("code", origin.code())
                .add("name", origin.name())
                .add("type", origin.type().name())
                .add("type_id", origin.type().id())
                .add("short_name", origin.type().shortName())
                .add("description", origin.description())
                .add("label", origin.label())                
                .add("names", Json.createArrayBuilder(origin.names()))
                .add("values", Json.createArrayBuilder(origin.values()))
                .add("increaseRates", Json.createArrayBuilder(origin.increaseRates()))
                .add("increaseInPercents", Json.createArrayBuilder(origin.increaseInPercents()))
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
