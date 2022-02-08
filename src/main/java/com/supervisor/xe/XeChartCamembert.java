package com.supervisor.xe;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonStructure;

import org.apache.commons.lang.StringUtils;
import org.takes.rs.RsJson;

import com.supervisor.indicator.ChartCamembert;

public final class XeChartCamembert implements RsJson.Source {

	private final ChartCamembert origin;
	
	public XeChartCamembert(final ChartCamembert origin) {
		this.origin = origin;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return Json.createObjectBuilder()
				.add("id", origin.id().toString())
                .add("code", origin.code())
                .add("name", origin.name())
                .add("type", origin.type().name())
                .add("type_id", origin.type().id().toString())
                .add("short_name", origin.type().shortName())
                .add("description", origin.description())
                .add("label", origin.label())
                .add("sub_label", StringUtils.defaultIfEmpty(origin.subLabel(), ""))
                .add("columns", Json.createArrayBuilder(origin.columns()))                 
                .add("names", Json.createArrayBuilder(origin.names()))
                .add("values", Json.createArrayBuilder(origin.values()))
				.add("camembert_type", origin.camembertType().toString())
				.add("camembert_type_id", origin.camembertType().name().toLowerCase())
				.add("activity_id", origin.activity().id().toString())
                .add("activity", origin.activity().name())
                .add("sizeX", origin.sizeX())
                .add("sizeY", origin.sizeY())
                .add("row", origin.row())
                .add("col", origin.col())
				.build();
	}
}
