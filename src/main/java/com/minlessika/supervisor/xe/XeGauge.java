package com.minlessika.supervisor.xe;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

import org.takes.rs.RsJson;


import com.minlessika.supervisor.indicator.Gauge;
import com.minlessika.supervisor.indicator.GaugeZone;

public final class XeGauge implements RsJson.Source {

	private final Gauge origin;
	
	public XeGauge(final Gauge origin) {
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
                .add("zones", zones(origin))                  
                .add("graduations", Json.createArrayBuilder(origin.graduations()))
                .add("number", origin.number())
				.add("gauge_type", origin.gaugeType().toString())
				.add("gauge_type_id", origin.gaugeType().name().toLowerCase())
				.add("gauge_type_short_name", origin.gaugeType().shortName())
				.add("unity_symbol", origin.unitySymbol())
				.add("min", origin.min())
				.add("max", origin.max())
				.add("minorTicks", origin.minorTicks())		
				.add("activity_id", origin.activity().id())
                .add("activity", origin.activity().name())
                .add("sizeX", origin.sizeX())
                .add("sizeY", origin.sizeY())
                .add("row", origin.row())
                .add("col", origin.col())
				.build();
	}
	
	private static JsonArrayBuilder zones(final Gauge gauge) throws IOException {
		
		JsonArrayBuilder zones = Json.createArrayBuilder();
		
		for (GaugeZone zone : gauge.zones().items()) {
			JsonObjectBuilder jZone = Json.createObjectBuilder()
					              .add("color", zone.color().toString())
					              .add("color_id", zone.color().name())
					              .add("color_code", zone.color().code())
					              .add("min", zone.min())
					              .add("max", zone.max());
			
			zones.add(jZone);
		}
		
		return zones;
	}
}
