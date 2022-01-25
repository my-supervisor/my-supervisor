package com.supervisor.takes;

import java.util.logging.Level;

import javax.json.JsonArray;
import javax.json.JsonObject;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.Request;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import com.minlessika.rq.RqJson;
import com.minlessika.rq.RqJsonBase;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.Indicator;

public final class TkActivityOrganize extends TkBaseWrap {

	public TkActivityOrganize(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					Activities myActivities = module.activities();
					
					final Request gReq = new RqGreedy(req);
					
					Long activityId = Long.parseLong(new RqHref.Smart(gReq).single("activity"));		
					Activity activity = myActivities.get(activityId);
					
					if(new RqUser(base, req).notOwn(activity)) {
						throw new IllegalArgumentException("Vous ne pouvez pas organiser une activité partagée !");
					}
						
					final RqJson rqJson = new RqJsonBase(gReq);					
					JsonArray locations = rqJson.payload().asJsonArray();
					for (JsonObject myLoc : locations.getValuesAs(JsonObject.class)) {
						
						Long id = myLoc.getJsonNumber("id").longValue();			
						Indicator indicator = activity.indicators().get(id);	
						
						final int sizeX = myLoc.getInt("sizeX");
						final int sizeY = myLoc.getInt("sizeY");
						final int row = myLoc.getInt("row");
						final int col = myLoc.getInt("col");
						
						indicator.locate(sizeX, sizeY, row, col);
					}
								
					return new RsForward(
						new RsFlash(
								"L'activité a été organisée avec succès !",
			                Level.FINE
			            ),
			            "/home?activity=" + activity.id()
				    );
				}
		);
	}
}
