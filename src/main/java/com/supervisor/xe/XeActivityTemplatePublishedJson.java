package com.supervisor.xe;

import java.io.IOException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonStructure;

import com.supervisor.domain.impl.UserOf;
import org.takes.rs.RsJson;

import com.supervisor.domain.User;
import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.ActivityTemplatesPublished;

public final class XeActivityTemplatePublishedJson implements RsJson.Source {

	private final ActivityTemplatesPublished origin;
	
	public XeActivityTemplatePublishedJson(final ActivityTemplatesPublished origin) {
		this.origin = origin;
	}
	
	@Override
	public JsonStructure toJson() throws IOException {
		return  Json.createObjectBuilder()
					.add("items", templatesJson(origin.items()))
	                .add("count", origin.count())
					.build();
	}
	
	private static JsonArray templatesJson(final List<ActivityTemplatePublished> templates) throws IOException {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		
		for (ActivityTemplatePublished item : templates) {
			
			final User user = new UserOf(item);
			
			builder.add(Json.createObjectBuilder()
							.add("id", item.id().toString())
			                .add("name", item.name())
			                .add("profile", item.profile().name())
			                .add("profile_id", item.profile().id().toString())
			                .add("description", item.description())
			                .add("icon", item.icon())
			                .add("version", item.version())
			                .add("nbViews", item.nbViews())
			                .add("nbLikes", item.nbLikes())
			                .add("nbPaid", item.nbPaid())
			                .add("nbSubscriptions", item.nbSubscriptions())
			                .add("score", item.score())
			                .add("price", item.price())
			                .add("availability", item.availability().toString())
			                .add("availabilityId", item.availability().name())     
			                .add("designer", item.designer().name())
			                .add("designerId", item.designer().id().toString())
			                .add("designerPhoto", item.designer().photo())
			                .add("fromNow", user.prettyTimeOf(item.creationDate()).toString())
	               );
		}
		
		return builder.build();
	}
}
